package com.brewery.web.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

@Table(value = "user_credential")
public class UserCredential {

    @Id
    @PrimaryKey
    @Column(value = "id")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID id;

    @Column(value = "create_date")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private Instant createDate;

    @Column(value = "update_date")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private Instant updateDate;

    @Column(value = "status")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String status;

    @Column(value = "user_id")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID userId;

    @Column(value = "special_key")
    @CassandraType(type = CassandraType.Name.TEXT)
    private byte[] specialKey;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public byte[] getSpecialKey() {
        return specialKey;
    }

    public void setSpecialKey(byte[] specialKey) {
        this.specialKey = specialKey;
    }

    @Override
    public String toString() {
        return "UserCredential{" +
                "id=" + id +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", status='" + status + '\'' +
                ", userId=" + userId +
                ", specialKey='" + Arrays.toString(specialKey) + '\'' +
                '}';
    }
}
