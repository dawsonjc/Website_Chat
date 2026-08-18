package com.brewery.web.model.message;

import com.brewery.web.model.RecordStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Table(value = "messages")
public class Message {
    @Id
    @Column(value = "messageid")
    @PrimaryKeyColumn(name = "messageid", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID messageId;

    @Column(value = "create_date")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private Instant createDate;

    @Column(value = "update_date")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private Instant updateDate;

    @Column(value = "status")
    @CassandraType(type = CassandraType.Name.TEXT)
    private RecordStatus status;

    @Column(value = "conversation_id")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID conversationId;

    @Column(value = "from_user_id")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID fromUserId;

    @Column(value = "from_username")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String fromUsername;

    @Column(value = "to_user_id")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID toUserId;

    @Column(value = "to_username")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String toUsername;

    @Column(value = "content")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String content;

    /**
     * @deprecated
     * Please use {@link #Message(MessageType)}
     */
    @Deprecated
    public Message() {}

    public Message(MessageType messageType) {
        switch(messageType) {
            case MessageType.FILL_DEFAULT -> {
                Instant now = Instant.now();
                this.messageId = UUID.randomUUID();
                this.createDate = now;
                this.updateDate = now;
                this.status = RecordStatus.ACTIVE;
            }
        }
    }

    public Message(
            UUID messageId, Instant createDate, Instant updateDate,
            RecordStatus status, UUID conversationId, UUID fromUserId, String fromUsername,
            UUID toUserId, String toUsername, String content
    ) {
        this.messageId = messageId;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.status = status;
        this.conversationId = conversationId;
        this.fromUserId = fromUserId;
        this.fromUsername = fromUsername;
        this.toUserId = toUserId;
        this.toUsername = toUsername;
        this.content = content;
    }

    public UUID getMessageId() {
        return messageId;
    }

    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public RecordStatus getStatus() {
        return this.status;
    }

    public void setStatus(RecordStatus status) {
        this.status = status;
    }

    public UUID getConversationId() {
        return conversationId;
    }

    public void setConversationId(UUID conversationId) {
        this.conversationId = conversationId;
    }

    public UUID getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(UUID fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    public UUID getToUserId() {
        return toUserId;
    }

    public void setToUserId(UUID toUserId) {
        this.toUserId = toUserId;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", status='" + status + '\'' +
                ", conversationId=" + conversationId +
                ", fromUserId=" + fromUserId +
                ", fromUsername=" + fromUsername +
                ", toUserId=" + toUserId +
                ", toUsername=" + toUsername +
                ", content='" + content + '\'' +
                '}';
    }
}
