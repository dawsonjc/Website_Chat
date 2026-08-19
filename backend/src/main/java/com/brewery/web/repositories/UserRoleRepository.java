package com.brewery.web.repositories;

import com.brewery.web.model.UserRole;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserRoleRepository extends CassandraRepository<UserRole, UUID> {

    @AllowFiltering
    @Query(value = "SELECT * FROM user_role WHERE userid = :userId ALLOW FILTERING", allowFiltering = true)
    public List<UserRole> getAllRoleByUserId(@Param(value = "userId") UUID userId);
}
