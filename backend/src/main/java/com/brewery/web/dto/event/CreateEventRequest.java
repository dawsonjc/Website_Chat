package com.brewery.web.dto.event;

import com.brewery.web.model.event.EventType;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

/** Input used by application services when creating a notification. */
public record CreateEventRequest(
        UUID userId,
        EventType eventType,
        UUID actorId,
        String actorName,
        String title,
        String message,
        String actionUrl,
        Map<String, String> metadata,
        Instant expiresAt
) {
}
