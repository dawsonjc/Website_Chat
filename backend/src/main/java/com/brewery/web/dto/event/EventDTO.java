package com.brewery.web.dto.event;

import com.brewery.web.model.record.RecordStatus;
import com.brewery.web.model.event.Event;
import com.brewery.web.model.event.EventType;
import com.brewery.web.model.event.EventsByType;
import com.brewery.web.model.event.UnreadEvent;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public record EventDTO(
        UUID userId,
        UUID eventId,
        Instant createDate,
        Instant updateDate,
        RecordStatus status,
        EventType eventType,
        UUID actorId,
        String actorName,
        String title,
        String message,
        String actionUrl,
        Map<String, String> metadata,
        Boolean isRead,
        Boolean isArchived,
        Instant expiresAt
) {
    public static EventDTO.Builder builder() {
        return new Builder();
    }

    public static EventDTO from(Event event) {
        return EventDTO.builder()
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
                .withIsRead(event.isRead())
                .withIsArchive(event.isArchive())
                .withExpiresAt(event.getExpiresAt())
                .build();
    }

    public static EventDTO from(EventsByType event) {
        return EventDTO.builder()
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

    public static EventDTO from(UnreadEvent event) {
        return EventDTO.builder()
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

    public static class Builder {
        private UUID userId;
        private UUID eventId;
        private Instant createDate;
        private Instant updateDate;
        private RecordStatus status;
        private EventType eventType;
        private UUID actorId;
        private String actorName;
        private String title;
        private String message;
        private String actionUrl;
        private Map<String, String> metadata;
        private boolean isRead;
        private boolean isArchive;
        private Instant expiresAt;

        private Builder() {}

        public EventDTO.Builder withUserId(UUID userId) {
            this.userId = userId;
            return this;
        }
        public EventDTO.Builder withEventId(UUID eventId) {
            this.eventId = eventId;
            return this;
        }
        public EventDTO.Builder withCreateDate(Instant createDate) {
            this.createDate = createDate;
            return this;
        }
        public EventDTO.Builder withUpdateDate(Instant updateDate) {
            this.updateDate = updateDate;
            return this;
        }
        public EventDTO.Builder withStatus(RecordStatus status) {
            this.status = status;
            return this;
        }
        public EventDTO.Builder withEventType(EventType eventType) {
            this.eventType = eventType;
            return this;
        }
        public EventDTO.Builder withActorId(UUID actorId) {
            this.actorId = actorId;
            return this;
        }
        public EventDTO.Builder withActorName(String actorName) {
            this.actorName = actorName;
            return this;
        }
        public EventDTO.Builder withTitle(String title) {
            this.title = title;
            return this;
        }
        public EventDTO.Builder withMessage(String message) {
            this.message = message;
            return this;
        }
        public EventDTO.Builder withActionUrl(String actionUrl) {
            this.actionUrl = actionUrl;
            return this;
        }
        public EventDTO.Builder withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }
        public EventDTO.Builder withIsRead(boolean isRead) {
            this.isRead = isRead;
            return this;
        }
        public EventDTO.Builder withIsArchive(boolean isArchive) {
            this.isArchive = isArchive;
            return this;
        }
        public EventDTO.Builder withExpiresAt(Instant expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public EventDTO build() {
            return new EventDTO(
                    this.userId,
                    this.eventId,
                    this.createDate,
                    this.updateDate,
                    this.status,
                    this.eventType,
                    this.actorId,
                    this.actorName,
                    this.title,
                    this.message,
                    this.actionUrl,
                    this.metadata,
                    this.isRead,
                    this.isArchive,
                    this.expiresAt
            );
        }



    }
}
