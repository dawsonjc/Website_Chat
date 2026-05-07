package com.brewery.web.repositories;

import com.brewery.web.model.Role;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoleRepository extends CassandraRepository<Role, UUID> {

    @AllowFiltering
    @Query(value = "SELECT * FROM roles WHERE role_name = :roleName ALLOW FILTERING", allowFiltering = true)
    public Role getRoleByName(@Param(value = "roleName") String roleName);

    @AllowFiltering
    @Query(value = "SELECT * FROM roles WHERE status = 'Active' ALLOW FILTERING", allowFiltering = true)
    public List<Role> getActiveRoles();
}
