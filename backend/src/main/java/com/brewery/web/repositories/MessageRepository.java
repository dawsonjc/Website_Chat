package com.brewery.web.repositories;

import com.brewery.web.model.message.Message;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface MessageRepository extends CassandraRepository<Message, UUID> {
    @AllowFiltering
    @Query(value = "SELECT * FROM messages_by_conversation_id WHERE conversation_id = :conversationId ORDER BY createdate LIMIT 50 ALLOW FILTERING", allowFiltering = true)
    public ArrayList<Message> getMessagesByConversationId(UUID conversationId);

    @AllowFiltering
    @Query(value = "SELECT * FROM messages WHERE messageid = :messageId", allowFiltering = true)
    public Message getByMessageId(@Param(value = "messageId") UUID messageId);
}
