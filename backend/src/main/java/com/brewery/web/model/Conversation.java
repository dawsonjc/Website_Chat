package com.brewery.web.model;

import com.brewery.web.model.record.RecordStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Table(value = "conversations")
public class Conversation {
    @Id
    @Column(value = "conversationid")
    @PrimaryKeyColumn(name = "conversationid", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID conversationId;

    @Column(value = "create_date")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private Instant createDate;

    @Column(value = "update_date")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private Instant updateDate;

    @Column(value = "status")
    @CassandraType(type = CassandraType.Name.TEXT)
    private RecordStatus status;

    @Column(value = "name")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String name;

    @Column(value = "users")
    @CassandraType(type = CassandraType.Name.SET, typeArguments = { CassandraType.Name.UUID })
    private Set<UUID> users;

    public Conversation() {}

    public Conversation(UUID conversationId, Instant createDate, Instant updateDate, RecordStatus status, String name, Set<UUID> users) {
        this.conversationId = conversationId;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.status = status;
        this.name = name;
        this.users = users;
    }

    public UUID getConversationId() {
        return conversationId;
    }

    public void setConversationId(UUID conversationId) {
        this.conversationId = conversationId;
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UUID> getUsers() {
        return users;
    }

    public void setUsers(Set<UUID> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "conversationId=" + conversationId +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", status=" + status +
                ", name='" + name + '\'' +
                ", users=" + users +
                '}';
    }
}
