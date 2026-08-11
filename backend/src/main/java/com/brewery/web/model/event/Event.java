package com.brewery.web.model.event;

import com.brewery.web.model.RecordStatus;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Table(value = "event")
@JsonDeserialize(builder = Event.Builder.class)
public class Event {
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

    @Column(value = "is_read")
    @CassandraType(type = CassandraType.Name.BOOLEAN)
    private boolean isRead;

    @Column(value = "is_archived")
    @CassandraType(type = CassandraType.Name.BOOLEAN)
    private boolean isArchive;

    @Column(value = "expires_at")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private Instant expiresAt;

    public static Event.Builder builder() {
        return new Builder();
    }

    public UUID getUserId() {
        return this.userId;
    }

    public UUID getEventId() {
        return this.eventId;
    }

    public Instant getCreateDate() {
        return this.createDate;
    }

    public Instant getUpdateDate() {
        return this.updateDate;
    }

    public RecordStatus getStatus() {
        return this.status;
    }

    public EventType getEventType() {
        return this.eventType;
    }

    public UUID getActorId() {
        return this.actorId;
    }

    public String getActorName() {
        return this.actorName;
    }

    public String getTitle() {
        return this.title;
    }

    public String getMessage() {
        return this.message;
    }

    public String getActionUrl() {
        return this.actionUrl;
    }

    public Map<String, String> getMetadata() {
        return this.metadata;
    }

    public boolean isRead() {
        return this.isRead;
    }

    public boolean isArchive() {
        return this.isArchive;
    }

    public Instant getExpiresAt() {
        return this.expiresAt;
    }

    @JsonPOJOBuilder
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

        public Event.Builder withUserId(UUID userId) {
            this.userId = userId;
            return this;
        }
        public Event.Builder withEventId(UUID id) {
            this.eventId = id;
            return this;
        }
        public Event.Builder withCreateDate(Instant createDate) {
            this.createDate = createDate;
            return this;
        }
        public Event.Builder withUpdateDate(Instant updateDate) {
            this.updateDate = updateDate;
            return this;
        }
        public Event.Builder withStatus(RecordStatus status) {
            this.status = status;
            return this;
        }
        public Event.Builder withEventType(EventType eventType) {
            this.eventType = eventType;
            return this;
        }
        public Event.Builder withActorId(UUID actorId) {
            this.actorId = actorId;
            return this;
        }
        public Event.Builder withActorName(String actorName) {
            this.actorName = actorName;
            return this;
        }
        public Event.Builder withTitle(String title) {
            this.title = title;
            return this;
        }
        public Event.Builder withMessage(String message) {
            this.message = message;
            return this;
        }
        public Event.Builder withActionUrl(String actionUrl) {
            this.actionUrl = actionUrl;
            return this;
        }
        public Event.Builder withMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }
        public Event.Builder withIsRead(boolean isRead) {
            this.isRead = isRead;
            return this;
        }
        public Event.Builder withIsArchive(boolean isArchive) {
            this.isArchive = isArchive;
            return this;
        }
        public Event.Builder withExpiresAt(Instant expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public Event build() {
            Event event = new Event();
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
            event.isRead = this.isRead;
            event.isArchive = this.isArchive;
            event.expiresAt = this.expiresAt;

            return event;
        }
    }
}
