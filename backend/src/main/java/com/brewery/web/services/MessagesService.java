package com.brewery.web.services;

import com.brewery.web.model.message.Message;
import com.brewery.web.model.User;
import com.brewery.web.model.message.MessageType;
import com.brewery.web.model.message.MessageView;
import com.brewery.web.repositories.MessageRepository;
import com.brewery.web.repositories.view.MessageViewRepository;
import com.brewery.web.stringutils.StringUtils;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service(value = "MessagesService")
public class MessagesService {

    @Autowired
    private UserTableService userService;

    @Autowired
    private MessageRepository repo;

    @Autowired
    private MessageViewRepository messageViewRepository;

    public Message findMessage(UUID id) {
        return repo.getByMessageId(id);
    }

    public List<Message> getMessagesByConversationId(UUID conversationId) {
        ArrayList<MessageView> views = this.messageViewRepository.getAllMessageViewsByConversationId(conversationId);
        return views.stream().map(MessageView::toMessage).collect(Collectors.toList());
    }

    public Message saveMessage(ObjectNode json) {
        Message message = new Message(MessageType.FILL_DEFAULT);
        message.setConversationId(UUID.fromString(json.get("conversationId").asText()));

        User user = this.userService.getUserById(UUID.fromString(json.get("fromUserId").asText()));

        if(user != null) {
            message.setFromUserId(user.getUserId());
            message.setFromUsername(user.getUsername());
        }
        if(json.has("to-user-id") && !json.get("to-user-id").asText().isEmpty()) {
            message.setToUserId(UUID.fromString(json.get("to-user-id").asText()));
        }
        if(json.has("to-username") && !json.get("to-username").asText().isEmpty()) {
            message.setToUsername(json.get("to-username").asText());
        }

        message.setContent(json.get("content").asText());

        String correctedContent = StringUtils.cleanseString(message.getContent());

        message.setContent(correctedContent);

        return repo.save(message);
    }

    public List<Message> getMessagesByConversationIdAndBeforeCreateDate(UUID conversationId, Instant createDate) {
        ArrayList<MessageView> views = this.messageViewRepository.getAllMessageViewsByConversationIdAndBeforeCreateDate(conversationId, createDate);
        return views.stream().map(MessageView::toMessage).collect(Collectors.toList());
    }

    public void deleteMessage(Message message) {
        message.setStatus("Disabled");

        this.repo.save(message);
    }
}
