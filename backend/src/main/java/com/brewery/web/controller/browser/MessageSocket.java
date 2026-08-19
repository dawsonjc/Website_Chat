package com.brewery.web.controller.browser;

import com.brewery.web.configuration.SpringContext;
import com.brewery.web.model.message.Message;
import com.brewery.web.model.User;
import com.brewery.web.services.MessagesService;
import com.brewery.web.services.UserTableService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.websocket.*;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint(value = "/communication")
public class MessageSocket {
    private final MessagesService messagesService;
    private final UserTableService userTableService;

    private static final ConcurrentHashMap<String, Set<Session>> conversationSessions  = new ConcurrentHashMap<String, Set<Session>>();

    public MessageSocket() {
        ApplicationContext context = SpringContext.getApplicationContext();
        this.messagesService = (MessagesService) context.getBean("MessagesService");
        this.userTableService = (UserTableService) context.getBean("UserTableService");
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        Map<String, List<String>> paramMap = session.getRequestParameterMap();
        if(!paramMap.containsKey("conversationId")) {
            return;
        }
        if(!paramMap.containsKey("userId")) {
            return;
        }

        // POTENTIAL SECURITY VULNERABILITY?
        // requestor would have to get *both* a correct user id and somehow guess that the conversationId
        // also exists in said user? Think about fixing? Auth Token?
        UUID conversationUUID = UUID.fromString(paramMap.get("conversationId").getFirst());
        String conversationId = conversationUUID.toString();
        String userId = paramMap.get("userId").getFirst();
        Map<String, Object> userProps = session.getUserProperties();

        User user = this.userTableService.getUserById(UUID.fromString(userId));
        List<com.brewery.web.dto.ConversationDTO> conversations = user.getConversations();
        if(conversations.stream().noneMatch((dto) -> {
            return dto.conversationId().equals(conversationUUID);
        })) { return; };
        userProps.put("conversationId", conversationId);
        userProps.put("userId", userId);
        conversationSessions.computeIfAbsent(conversationId, (k) -> {
            return ConcurrentHashMap.newKeySet();
        }).add(session);
    }

    @OnClose
    public void onClose(Session session) {
        String conversationId = session.getRequestParameterMap().get("conversationId").getFirst();

        Set<Session> sessions = conversationSessions.get(conversationId);
        if(sessions == null) {
            System.err.println("Tried to remove " + session + " but conversationSessions get on id " + conversationId + " returned null.");
            return;
        }
        sessions.remove(session);
        if(sessions.isEmpty()) {
            conversationSessions.remove(conversationId);
        }
    }

    @OnMessage
    public void onMessage(String messageJson, Session session) {
        ObjectMapper mapper = new ObjectMapper();
        String conversationId = (String) session.getUserProperties().get("conversationId");
        Set<Session> sessions = conversationSessions.get(conversationId);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")
                .withLocale(java.util.Locale.US)
                .withZone(java.time.ZoneId.of("UTC"));

        Message savedMessage;
        try {
            ObjectNode json = (ObjectNode) mapper.readTree(messageJson);
            savedMessage = this.messagesService.saveMessage(json);
        } catch (IOException exception) {
            exception.printStackTrace();
            return;
        }

        ObjectNode responseJson = mapper.createObjectNode();
        responseJson.put("success", false);
        responseJson.put("message", "");
        ObjectNode data = responseJson.putObject("data");

        responseJson.put("success", true);
        data.put("messageId", savedMessage.getMessageId().toString());
        data.put("createDate", dateTimeFormatter.format(savedMessage.getCreateDate()));
        data.put("fromUserId", savedMessage.getFromUserId().toString());
        data.put("fromUsername", savedMessage.getFromUsername());
        data.put("content", savedMessage.getContent());

        for(Session session1 : sessions) {
            if(session1.isOpen()) {
                session1.getAsyncRemote().sendText(responseJson.toString());
            }
        }
    }

    public static void broadcast(UUID conversationId, ObjectNode json) throws IOException {
        Set<Session> sessions = conversationSessions.get(conversationId.toString());
        for(Session session : sessions) {
            session.getAsyncRemote().sendText(json.toString());
        }
    }

    private void printParams(EndpointConfig config) {
        Map<String, Object> paramMap = config.getUserProperties();
        for(Map.Entry<String, Object> s : paramMap.entrySet()) {
            System.err.println(s.getKey() + " -> " + s.getValue());
        }
    }

    private void printParams(Session session) {
        Map<String, List<String>> paramMap = session.getRequestParameterMap();
        for(Map.Entry<String, List<String>> s : paramMap.entrySet()) {
            System.err.println(s.getKey() + " -> " + s.getValue());
        }
    }

    public static class SessionManager {
        private static final Set<Session> sessions = ConcurrentHashMap.newKeySet();

        public static void addSession(Session session) {
            sessions.add(session);
        }

        public static void removeSession(Session session) {
            sessions.remove(session);
        }

        public static void broadcast(String message) {
            for(Session session : sessions) {
                if (session.isOpen()) {
                    session.getAsyncRemote().sendText(message);
                }
            }
        }
    }
}
