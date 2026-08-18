package com.brewery.web.model.event;

import com.brewery.web.model.record.RecordStatus;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public class UnreadEvent {
    @PrimaryKeyColumn(name ="user_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID userId;

    @PrimaryKeyColumn(name ="event_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID eventId;

    @Column(value = "create_date")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private Instant createDate;

    @Column(value = "update_date")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private Instant updateDate;

    @Column(value = "status")
    @CassandraType(type = CassandraType.Name.TEXT)
    private RecordStatus status;

    @Column(value = "event_type")
    @CassandraType(type = CassandraType.Name.TEXT)
    private EventType eventType;

    @Column(value = "actor_id")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID actorId;

    @Column(value = "actor_name")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String actorName;

    @Column(value = "title")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String title;

    @Column(value = "message")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String message;

    @Column(value = "action_url")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String actionUrl;

    @Column(value = "metadata")
    @CassandraType(type = CassandraType.Name.MAP, typeArguments = { CassandraType.Name.TEXT, CassandraType.Name.TEXT })
    private Map<String, String> metadata;

    @Column(value = "expires_at")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private Instant expiresAt;

    private UnreadEvent() {}

    public UUID getUserId() {
        return this.userId;
    }

    public UUID getEventId() {
        return eventId;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public RecordStatus getStatus() {
        return status;
    }

    public EventType getEventType() {
        return eventType;
    }

    public UUID getActorId() {
        return this.actorId;
    }

    public String getActorName() {
        return this.actorName;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public Instant getExpiresAt() {
        return this.expiresAt;
    }

    private static class Builder {
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
        private Instant expiresAt;

        private Builder() {}

        public UnreadEvent.Builder withUserId(UUID userId) {
            this.userId = userId;
            return this;
        }
        public UnreadEvent.Builder withEventId(UUID id) {
            this.eventId = id;
            return this;
        }
        public UnreadEvent.Builder withCreateDate(Instant createDate) {
            this.createDate = createDate;
            return this;
        }
        public UnreadEvent.Builder withUpdateDate(Instant updateDate) {
            this.updateDate = updateDate;
            return this;
        }
        public UnreadEvent.Builder withStatus(RecordStatus status) {
            this.status = status;
            return this;
        }
        public UnreadEvent.Builder withEventType(EventType eventType) {
            this.eventType = eventType;
            return this;
        }
        public UnreadEvent.Builder withActorId(UUID actorId) {
            this.actorId = actorId;
            return this;
        }
        public UnreadEvent.Builder withActorName(String actorName) {
            this.actorName = actorName;
            return this;
        }
        public UnreadEvent.Builder withTitle(String title) {
            this.title = title;
            return this;
        }
        public UnreadEvent.Builder withMessage(String message) {
            this.message = message;
            return this;
        }
        public UnreadEvent.Builder withActionUrl(String actionUrl) {
            this.actionUrl = actionUrl;
            return this;
        }
        public UnreadEvent.Builder withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }
        public UnreadEvent.Builder withExpiresAt(Instant expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public UnreadEvent build() {
            UnreadEvent event = new UnreadEvent();
            event.userId = this.userId;
            event.eventId = this.eventId;
            event.createDate = this.createDate;
            event.updateDate = this.updateDate;
            event.status = this.status;
            event.eventType = this.eventType;
            event.actorId = this.actorId;
            event.actorName = this.actorName;
            event.title = this.title;
            event.message = this.message;
            event.actionUrl = this.actionUrl;
            event.metadata = this.metadata;
            event.expiresAt = this.expiresAt;

            return event;
        }
    }
}
