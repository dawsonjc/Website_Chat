package com.brewery.web.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Table(value = "user_roles")
public class UserRole {

    @Column(value = "create_date")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private Instant createDate;

    @Column(value = "update_date")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private Instant updateDate;

    @Column(value = "status")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String status;

    @Column(value = "roleid")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID roleId;

    @Column(value = "role_name")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String roleName;

    @Id
    @Column(value = "userid")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID userId;

    public UserRole() {}

    public UserRole(Instant createDate, Instant updateDate, String status, UUID roleId, String roleName, UUID userId) {
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.status = status;
        this.roleId = roleId;
        this.roleName = roleName;
        this.userId = userId;
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

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
