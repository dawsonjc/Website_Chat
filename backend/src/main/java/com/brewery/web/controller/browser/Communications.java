package com.brewery.web.controller.browser;

import com.brewery.web.dto.ConversationDTO;
import com.brewery.web.model.Role;
import com.brewery.web.model.message.Message;
import com.brewery.web.model.User;
import com.brewery.web.services.MessagesService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RestController
public class Communications {

    @Autowired
    private MessagesService service;

    @GetMapping(value = { "/message" })
    public ResponseEntity<ObjectNode> getMessageBeforeDate(
            HttpServletRequest request,
            @RequestParam(value = "conversationId") UUID conversationId,
            @RequestParam(value = "beforeDate") String createDate
    ) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseJson = mapper.createObjectNode();
        responseJson.put("success", false);
        responseJson.put("message", "");
        ArrayNode node = responseJson.putArray("data");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")
                .withZone(ZoneOffset.UTC);
        LocalDateTime dateTime = LocalDateTime.parse(createDate, formatter);
        Instant instant = dateTime.toInstant(ZoneOffset.UTC);

        List<Message> messagesBeforeDate = this.service.getMessagesByConversationIdAndBeforeCreateDate(conversationId, instant);

        for(Message message : messagesBeforeDate) {
            node.addPOJO(message);
        }

        responseJson.put("success", true);
        responseJson.put("message", "");

        return ResponseEntity.ok(responseJson);
    }

    @ResponseBody
    @DeleteMapping(value = "/message/delete")
    public ResponseEntity<ObjectNode> deleteMessage(HttpServletRequest request, @RequestParam(value = "conversationId") UUID conversationId, @RequestParam(value = "messageId") UUID messageId) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseJson = mapper.createObjectNode();
        responseJson.put("success", false);
        responseJson.put("message", "");
        ObjectNode data = responseJson.putObject("data");

        User user = (User) request.getSession().getAttribute("current_user");
        Message message = this.service.findMessage(messageId);

        if(user.getRoles().contains(Role.Name.ADMIN.toString())) {
            this.service.deleteMessage(message);
            responseJson.put("success", true);

            data.put("IsAdmin", true);
            this.broadcast(conversationId, message);

            return ResponseEntity.status(HttpStatus.OK).body(responseJson);
        }

        List<ConversationDTO> userConversations = user.getConversations();

        if(userConversations.stream().noneMatch((dto) -> dto.conversationId().equals(conversationId))) {
            System.err.println("User does not have access to conversation BY conversationId");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseJson);
        };

        if(!message.getFromUserId().equals(user.getUserId())) {
            System.err.println("User does not have access to conversation BY id");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseJson);
        }

        responseJson.put("success", true);
        this.service.deleteMessage(message);

        this.broadcast(conversationId, message);

        return ResponseEntity.status(HttpStatus.OK).body(responseJson);
    }

    private void broadcast(UUID conversationId, Message message) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode deleteJson = mapper.createObjectNode();
        deleteJson.put("success", true);
        deleteJson.put("message", "deleted");

        ObjectNode deleteData = deleteJson.putObject("data");
        deleteData.put("messageId", message.getMessageId().toString());
        deleteData.put("type", "delete");
        try {
            MessageSocket.broadcast(conversationId, deleteJson);
        } catch(IOException ignore) {}
    }
}
