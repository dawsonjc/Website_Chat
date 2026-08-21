package com.brewery.web.services;

import com.brewery.web.dto.event.CreateEventRequest;
import com.brewery.web.dto.event.EventDTO;
import com.brewery.web.dto.event.NotificationSnapshotDTO;
import com.brewery.web.model.event.Event;
import com.brewery.web.model.event.EventType;
import com.brewery.web.model.event.EventsByType;
import com.brewery.web.model.event.UnreadEvent;
import com.brewery.web.model.record.RecordStatus;
import com.brewery.web.repositories.EventRepository;
import com.brewery.web.repositories.EventsByTypeRepository;
import com.brewery.web.repositories.UnreadEventRepository;
import com.brewery.web.sse.SseEventService;
import com.datastax.oss.driver.api.core.uuid.Uuids;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/** Owns notification persistence and emits SSE updates only after writes succeed. */
@Service
public class NotificationService {
    public static final int DEFAULT_LIST_LIMIT = 25;
    public static final int MAX_LIST_LIMIT = 100;

    private final EventRepository eventRepository;
    private final EventsByTypeRepository eventsByTypeRepository;
    private final UnreadEventRepository unreadEventRepository;
    private final SseEventService sse;

    public NotificationService(
            EventRepository eventRepository,
            EventsByTypeRepository eventsByTypeRepository,
            UnreadEventRepository unreadEventRepository,
            SseEventService sse
    ) {
        this.eventRepository = eventRepository;
        this.eventsByTypeRepository = eventsByTypeRepository;
        this.unreadEventRepository = unreadEventRepository;
        this.sse = sse;
    }

    /** Creates all Cassandra projections, then notifies the recipient live. */
    public EventDTO create(CreateEventRequest request) {
        validateCreateRequest(request);
        Instant now = Instant.now();
        UUID eventId = Uuids.timeBased();

        Event event = Event.builder()
                .withUserId(request.userId())
                .withEventId(eventId)
                .withCreateDate(now)
                .withUpdateDate(now)
                .withStatus(RecordStatus.ACTIVE)
                .withEventType(request.eventType())
                .withActorId(request.actorId())
                .withActorName(request.actorName())
                .withTitle(request.title())
                .withMessage(request.message())
                .withActionUrl(request.actionUrl())
                .withMetadata(request.metadata() == null ? Map.of() : request.metadata())
                .withIsRead(false)
                .withIsArchive(false)
                .withExpiresAt(request.expiresAt())
                .build();

        this.eventRepository.save(event);
        this.eventsByTypeRepository.save(toTypeProjection(event));
        this.unreadEventRepository.save(toUnreadProjection(event));

        EventDTO dto = EventDTO.from(event);
        this.sse.publishCreated(dto, unreadCount(request.userId()));
        return dto;
    }

    public NotificationSnapshotDTO snapshot(UUID userId, EventType type, int requestedLimit) {
        int limit = normalizeLimit(requestedLimit);
        List<EventDTO> notifications;
        if (type == null) {
            notifications = this.eventRepository.findRecentByUserId(userId, limit).stream()
                    .filter(this::isVisible)
                    .map(EventDTO::from)
                    .toList();
        } else {
            notifications = this.eventsByTypeRepository
                    .findRecentByUserIdAndType(userId, type, limit).stream()
                    .filter(this::isVisible)
                    .map(EventDTO::from)
                    .toList();
        }
        return new NotificationSnapshotDTO(notifications, unreadCount(userId));
    }

    public long unreadCount(UUID userId) {
        return this.unreadEventRepository.countByUserId(userId);
    }

    public EventDTO markRead(UUID userId, UUID eventId) {
        Event updated = markReadPersisted(userId, eventId);
        EventDTO dto = EventDTO.from(updated);
        sse.publishUpdated(dto, unreadCount(userId));
        return dto;
    }

    public NotificationSnapshotDTO markAllRead(UUID userId) {
        List<UnreadEvent> unreadEvents = this.unreadEventRepository.findByUserId(userId);
        for (UnreadEvent unread : unreadEvents) {
            markReadPersisted(userId, unread.getEventId());
        }
        NotificationSnapshotDTO snapshot = snapshot(userId, null, DEFAULT_LIST_LIMIT);
        sse.publishSnapshot(userId, snapshot);
        sse.publishUnreadCount(userId, snapshot.unreadCount());
        return snapshot;
    }

    public EventDTO archive(UUID userId, UUID eventId) {
        Event current = requireEvent(userId, eventId);
        Event archived = copyWithState(current, true, true, Instant.now());
        this.eventRepository.save(archived);
        this.eventsByTypeRepository.save(toTypeProjection(archived));
        this.unreadEventRepository.deleteOne(userId, eventId);

        EventDTO dto = EventDTO.from(archived);
        sse.publishUpdated(dto, unreadCount(userId));
        return dto;
    }

    private Event markReadPersisted(UUID userId, UUID eventId) {
        Event current = requireEvent(userId, eventId);
        if (current.isRead()) {
            return current;
        }
        Event updated = copyWithState(current, true, current.isArchive(), Instant.now());
        this.eventRepository.save(updated);
        this.eventsByTypeRepository.save(toTypeProjection(updated));
        this.unreadEventRepository.deleteOne(userId, eventId);
        return updated;
    }

    private Event requireEvent(UUID userId, UUID eventId) {
        Event event = eventRepository.findOne(userId, eventId);
        if (event == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Notification not found");
        }
        return event;
    }

    private Event copyWithState(Event source, boolean isRead, boolean isArchived, Instant updateDate) {
        return Event.builder()
                .withUserId(source.getUserId())
                .withEventId(source.getEventId())
                .withCreateDate(source.getCreateDate())
                .withUpdateDate(updateDate)
                .withStatus(source.getStatus())
                .withEventType(source.getEventType())
                .withActorId(source.getActorId())
                .withActorName(source.getActorName())
                .withTitle(source.getTitle())
                .withMessage(source.getMessage())
                .withActionUrl(source.getActionUrl())
                .withMetadata(source.getMetadata())
                .withIsRead(isRead)
                .withIsArchive(isArchived)
                .withExpiresAt(source.getExpiresAt())
                .build();
    }

    private EventsByType toTypeProjection(Event event) {
        return EventsByType.builder()
                .withUserId(event.getUserId())
                .withEventType(event.getEventType())
                .withEventId(event.getEventId())
                .withCreateDate(event.getCreateDate())
                .withUpdateDate(event.getUpdateDate())
                .withStatus(event.getStatus())
                .withActorId(event.getActorId())
                .withActorName(event.getActorName())
                .withTitle(event.getTitle())
                .withMessage(event.getMessage())
                .withActionUrl(event.getActionUrl())
                .withMetadata(event.getMetadata())
                .withIsRead(event.isRead())
                .withIsArchive(event.isArchive())
                .withExpiresAt(event.getExpiresAt())
                .build();
    }

    private UnreadEvent toUnreadProjection(Event event) {
        return UnreadEvent.builder()
                .withUserId(event.getUserId())
                .withEventId(event.getEventId())
                .withCreateDate(event.getCreateDate())
                .withUpdateDate(event.getUpdateDate())
                .withStatus(event.getStatus())
                .withEventType(event.getEventType())
                .withActorId(event.getActorId())
                .withActorName(event.getActorName())
                .withTitle(event.getTitle())
                .withMessage(event.getMessage())
                .withActionUrl(event.getActionUrl())
                .withMetadata(event.getMetadata())
                .withExpiresAt(event.getExpiresAt())
                .build();
    }

    private boolean isVisible(Event event) {
        return event.getStatus() == RecordStatus.ACTIVE
                && !event.isArchive()
                && (event.getExpiresAt() == null || event.getExpiresAt().isAfter(Instant.now()));
    }

    private boolean isVisible(EventsByType event) {
        return event.getStatus() == RecordStatus.ACTIVE
                && !event.isArchive()
                && (event.getExpiresAt() == null || event.getExpiresAt().isAfter(Instant.now()));
    }

    private int normalizeLimit(int limit) {
        if (limit <= 0) {
            return DEFAULT_LIST_LIMIT;
        }
        return Math.min(limit, MAX_LIST_LIMIT);
    }

    private void validateCreateRequest(CreateEventRequest request) {
        if (request == null || request.userId() == null || request.eventType() == null) {
            throw new IllegalArgumentException("Notification userId and eventType are required");
        }
    }
}
