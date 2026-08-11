package com.brewery.web.model.message;

import com.brewery.web.model.RecordStatus;
import com.brewery.web.model.message.Message;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Table(value = "messages_by_conversation_id")
public class MessageView {
    @Id
    @Column(value = "conversationid")
    @PrimaryKeyColumn(name = "conversationid", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID conversationId;

    @Column(value = "messageid")
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

    public MessageView() {}

    public MessageView(UUID messageId, Instant createDate, Instant updateDate,
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

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public Message toMessage() {
        Message message = new Message(MessageType.GENERIC);

        message.setMessageId(this.messageId);
        message.setCreateDate(this.createDate);
        message.setUpdateDate(this.updateDate);
        message.setStatus(this.status);
        message.setConversationId(this.conversationId);
        message.setFromUserId(this.fromUserId);
        message.setFromUsername(this.fromUsername);
        message.setToUserId(this.toUserId);
        message.setToUsername(this.toUsername);
        message.setContent(this.content);

        return message;
    }
}
