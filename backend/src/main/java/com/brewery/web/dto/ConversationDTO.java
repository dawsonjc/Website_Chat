package com.brewery.web.dto;

import com.brewery.web.model.Conversation;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record ConversationDTO(
        UUID conversationId,
        Instant createDate,
        Instant updateDate,
        String status,
        String name,
        Set<UUID> users
) {
    public static ConversationDTO fromEntity(Conversation conversation) {
        return new ConversationDTO(
                conversation.getConversationId(),
                conversation.getCreateDate(),
                conversation.getUpdateDate(),
                conversation.getStatus(),
                conversation.getName(),
                conversation.getUsers()
        );
    }

    public Conversation toEntity() {
        return new Conversation(
                conversationId,
                createDate,
                updateDate,
                status,
                name,
                users
        );
    }
}
