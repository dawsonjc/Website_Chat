package com.brewery.web.services;

import com.brewery.web.dto.ConversationDTO;
import com.brewery.web.model.Conversation;
import com.brewery.web.repositories.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class ConversationService {
    @Autowired
    private ConversationRepository conversationRepository;

    public ConversationDTO getGlobalChat() {
        return ConversationDTO.fromEntity(this.conversationRepository.getGlobalChat());
    }
    
    public java.util.ArrayList<ConversationDTO> getConversationsByUserId(UUID userId) {
        java.util.ArrayList<Conversation> conversations = this.conversationRepository.getConversationsWhereUserIdInUsers(userId);
        java.util.ArrayList<ConversationDTO> dtos = new ArrayList<ConversationDTO>(conversations.size());
        for(Conversation c : conversations) {
            dtos.add(ConversationDTO.fromEntity(c));
        }
        return dtos;
    }

    public ConversationDTO getConversationById(UUID id) {
        Conversation convo = this.conversationRepository.findById(id).orElse(null);
        if(convo == null) {
            return null;
        }
        return ConversationDTO.fromEntity(convo);
    }

    public void save(Conversation conversation) {
        this.conversationRepository.save(conversation);
    }
}
