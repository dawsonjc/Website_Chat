package com.brewery.web.repositories;

import com.brewery.web.model.Conversation;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConversationRepository extends CassandraRepository<Conversation, UUID> {
    @AllowFiltering
    @Query(value = "SELECT * FROM conversations WHERE name = 'Global Chat' LIMIT 1 ALLOW FILTERING", allowFiltering = true)
    public Conversation getGlobalChat();

    @AllowFiltering
    @Query(value = "SELECT * FROM conversations WHERE users CONTAINS :userId ALLOW FILTERING", allowFiltering = true)
    public java.util.ArrayList<Conversation> getConversationsWhereUserIdInUsers(@Param(value = "userId") UUID userId);
}
