package com.brewery.web.repositories;

import com.brewery.web.model.User;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserTableRepository extends CassandraRepository<User, UUID> {
    @Query(value = "SELECT userid FROM user_table WHERE email = :email AND status='Active' ALLOW FILTERING", allowFiltering = true)
    public UUID findUserIdByEmail(
            @Param(value = "email") String email
    );

    @AllowFiltering
    @Query(value = "SELECT * FROM user_table WHERE account_verification_status = 'Unverified' AND status='Active' ALLOW FILTERING", allowFiltering = true)
    public List<User> getUnverifiedUsers();

}
