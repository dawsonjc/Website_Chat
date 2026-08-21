package com.brewery.web.sse;

import com.brewery.web.dto.event.EventDTO;
import com.brewery.web.dto.event.NotificationSnapshotDTO;
import com.brewery.web.dto.event.UnreadCountDTO;
import com.brewery.web.model.User;
import com.brewery.web.model.event.EventType;
import com.brewery.web.services.NotificationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Locale;
import java.util.UUID;

/** Authenticated notification API consumed by the page header. */
@RestController
@RequestMapping("/api/events")
public class SseEventController {
    private final NotificationService notifications;
    private final SseEventService sse;

    public SseEventController(NotificationService notifications, SseEventService sse) {
        this.notifications = notifications;
        this.sse = sse;
    }

    /** Returns the current notification list and unread count in one request. */
    @GetMapping
    public NotificationSnapshotDTO list(
            HttpServletRequest request,
            @RequestParam(defaultValue = "25") int limit,
            @RequestParam(required = false) String type
    ) {
        return this.notifications.snapshot(userId(request), parseType(type), limit);
    }

    @GetMapping("/count")
    public UnreadCountDTO unreadCount(HttpServletRequest request) {
        return new UnreadCountDTO(this.notifications.unreadCount(userId(request)));
    }

    /** Opens the live stream and immediately emits a notifications_snapshot event. */
    @GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream(
            HttpServletRequest request,
            @RequestParam(defaultValue = "25") int limit
    ) {
        UUID userId = userId(request);
        return sse.subscribe(userId, () -> this.notifications.snapshot(userId, null, limit));
    }

    @PatchMapping("/{eventId}/read")
    public EventDTO markRead(HttpServletRequest request, @PathVariable UUID eventId) {
        return this.notifications.markRead(userId(request), eventId);
    }

    @PatchMapping("/read-all")
    public NotificationSnapshotDTO markAllRead(HttpServletRequest request) {
        return this.notifications.markAllRead(userId(request));
    }

    @PatchMapping("/{eventId}/archive")
    public EventDTO archive(HttpServletRequest request, @PathVariable UUID eventId) {
        return this.notifications.archive(userId(request), eventId);
    }

    private UUID userId(HttpServletRequest request) {
        // AuthHook protects this route and establishes current_user.
        User user = (User) request.getSession().getAttribute("current_user");
        return user.getUserId();
    }

    private EventType parseType(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return EventType.fromCode(value.toLowerCase(Locale.ROOT));
        } catch (IllegalArgumentException ignored) {
            return EventType.valueOf(value.toUpperCase(Locale.ROOT));
        }
    }
}
