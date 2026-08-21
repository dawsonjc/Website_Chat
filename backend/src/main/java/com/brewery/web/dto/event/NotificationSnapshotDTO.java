package com.brewery.web.dto.event;

import java.util.List;

public record NotificationSnapshotDTO(
        List<EventDTO> notifications,
        long unreadCount
) {
}
