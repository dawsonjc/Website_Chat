package com.brewery.web.repositories;

import com.brewery.web.model.UserCredential;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserCredentialRepository extends CassandraRepository<UserCredential, UUID> {

    @AllowFiltering
    @Query(value = "SELECT * FROM user_credential WHERE user_id = :userId ALLOW FILTERING")
    public UserCredential getUserCredentialByUserId(
            @Param(value = "userId") UUID userId
    );
}
