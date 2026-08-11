package com.brewery.web.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Table(value = "roles")
public class Role {
    public enum Name {
        ADMIN("Admin"),
        MODERATOR("Moderator"),
        USER("User");
        private final String name;

        Name(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }

    };

    @Id
    @Column(value = "roleid")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID roleId;

    @Column(value = "create_date")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private Instant createDate;

    @Column(value = "update_date")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private Instant updateDate;

    @Column(value = "status")
    @CassandraType(type = CassandraType.Name.TEXT)
    private RecordStatus status;

    @Column(value = "role_name")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String roleName;

    @Column(value = "description")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String description;

    public Role() {}

    public Role(UUID roleId, Instant createDate, Instant updateDate, RecordStatus status, String roleName, String description) {
        this.roleId = roleId;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.status = status;
        this.roleName = roleName;
        this.description = description;
    }

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
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
        return status;
    }

    public void setStatus(RecordStatus status) {
        this.status = status;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
