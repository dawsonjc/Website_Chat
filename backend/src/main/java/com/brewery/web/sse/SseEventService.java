package com.brewery.web.sse;

import com.brewery.web.dto.event.EventDTO;
import com.brewery.web.dto.event.NotificationSnapshotDTO;
import com.brewery.web.dto.event.UnreadCountDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/** In-memory notification streams grouped by recipient user ID. */
@Service
public class SseEventService {
    private static final long CONNECTION_TIMEOUT = Duration.ofMinutes(30).toMillis();
    private static final long RECONNECT_DELAY = Duration.ofSeconds(3).toMillis();

    private final Map<UUID, Map<UUID, SseEmitter>> clientsByUser = new ConcurrentHashMap<>();

    public SseEmitter subscribe(UUID userId, Supplier<NotificationSnapshotDTO> snapshotSupplier) {
        UUID clientId = UUID.randomUUID();
        SseEmitter emitter = new SseEmitter(CONNECTION_TIMEOUT);
        clientsByUser.computeIfAbsent(userId, ignored -> new ConcurrentHashMap<>())
                .put(clientId, emitter);

        Runnable removeClient = () -> removeClient(userId, clientId);
        emitter.onCompletion(removeClient);
        emitter.onTimeout(() -> {
            removeClient.run();
            emitter.complete();
        });
        emitter.onError(error -> removeClient.run());

        try {
            emitter.send(SseEmitter.event()
                    .id(UUID.randomUUID().toString())
                    .name("connected")
                    .reconnectTime(RECONNECT_DELAY)
                    .data(Map.of("clientId", clientId.toString(), "userId", userId.toString())));
            NotificationSnapshotDTO snapshot = snapshotSupplier.get();
            emitter.send(SseEmitter.event()
                    .id(UUID.randomUUID().toString())
                    .name("notifications_snapshot")
                    .reconnectTime(RECONNECT_DELAY)
                    .data(snapshot));
        } catch (IOException exception) {
            removeClient.run();
            emitter.completeWithError(exception);
        }
        return emitter;
    }

    public void publishCreated(EventDTO event, long unreadCount) {
        this.send(event.userId(), "notification_created", event.eventId(), event);
        this.publishUnreadCount(event.userId(), unreadCount);
    }

    public void publishUpdated(EventDTO event, long unreadCount) {
        this.send(event.userId(), "notification_updated", event.eventId(), event);
        this.publishUnreadCount(event.userId(), unreadCount);
    }

    public void publishSnapshot(UUID userId, NotificationSnapshotDTO snapshot) {
        this.send(userId, "notifications_snapshot", null, snapshot);
    }

    public void publishUnreadCount(UUID userId, long unreadCount) {
        this.send(userId, "notification_count", null, new UnreadCountDTO(unreadCount));
    }

    private int send(UUID userId, String eventName, UUID eventId, Object data) {
        if (userId == null) {
            throw new IllegalArgumentException("SSE recipient userId is required");
        }
        Map<UUID, SseEmitter> clients = this.clientsByUser.get(userId);
        if (clients == null) {
            return 0;
        }

        String sseId = eventId == null ? UUID.randomUUID().toString() : eventId.toString();
        int sent = 0;
        for (Map.Entry<UUID, SseEmitter> client : clients.entrySet()) {
            try {
                client.getValue().send(SseEmitter.event()
                        .id(sseId)
                        .name(eventName)
                        .reconnectTime(RECONNECT_DELAY)
                        .data(data));
                sent++;
            } catch (IOException | IllegalStateException exception) {
                removeClient(userId, client.getKey());
                client.getValue().complete();
            }
        }
        return sent;
    }

    private void removeClient(UUID userId, UUID clientId) {
        Map<UUID, SseEmitter> clients = this.clientsByUser.get(userId);
        if (clients == null) {
            return;
        }
        clients.remove(clientId);
        if (clients.isEmpty()) {
            this.clientsByUser.remove(userId, clients);
        }
    }
}
