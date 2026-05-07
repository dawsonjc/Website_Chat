package com.brewery.web.repositories.view;

import com.brewery.web.model.message.MessageView;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface MessageViewRepository extends CassandraRepository<MessageView, UUID> {

    @AllowFiltering
    @Query(value = "SELECT * FROM messages_by_conversation_id WHERE conversation_id = :conversationId AND status = 'Active' ORDER BY create_date DESC LIMIT 50 ALLOW FILTERING", allowFiltering = true)
    public ArrayList<MessageView> getAllMessageViewsByConversationId(UUID conversationId);

    @AllowFiltering
    @Query(value = "SELECT * FROM messages_by_conversation_id WHERE conversation_id = :conversationId AND create_date < :createDate AND status = 'Active' ORDER BY create_date DESC LIMIT 50 ALLOW FILTERING", allowFiltering = true)
    public ArrayList<MessageView> getAllMessageViewsByConversationIdAndBeforeCreateDate(
            @Param(value = "conversationId") UUID conversationId,
            @Param(value = "createDate") Instant createDate
    );
}
