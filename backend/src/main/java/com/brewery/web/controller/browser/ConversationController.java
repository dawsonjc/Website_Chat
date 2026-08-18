package com.brewery.web.controller.browser;

import com.brewery.web.dto.ConversationDTO;
import com.brewery.web.model.Conversation;
import com.brewery.web.model.record.RecordStatus;
import com.brewery.web.model.message.Message;
import com.brewery.web.model.User;
import com.brewery.web.services.ConversationService;
import com.brewery.web.services.MessagesService;
import com.brewery.web.services.UserTableService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class ConversationController {
    @Autowired
    private ConversationService conversationService;

    @Autowired
    private MessagesService messagesService;

    @Autowired
    private UserTableService userService;

    @GetMapping(value = { "/conversations" })
    public String doGet(HttpServletRequest request, HttpServletResponse response,
                        @RequestParam(value = "conversationId") UUID conversationId
    ) {
        User user = (User) request.getSession().getAttribute("current_user");
        List<Message> messages = this.messagesService.getMessagesByConversationId(conversationId);

        ConversationDTO globalChat = this.conversationService.getGlobalChat();

        if(!conversationId.equals(globalChat.conversationId())) {
            ConversationDTO convo = this.conversationService.getConversationById(conversationId);

            if(convo == null || !convo.users().contains(user.getUserId())) {
                return "redirect:/conversations?conversationId=" + globalChat.conversationId();
            }
        }
        ArrayList<ConversationDTO> channels = new ArrayList<ConversationDTO>(user.getConversations());

        request.setAttribute("Messages", messages);
        request.setAttribute("Channels", channels);

        return "index";
    }

    @PostMapping(value = { "/add-friend" })
    @ResponseBody
    public ResponseEntity<ObjectNode> addFriend(HttpServletRequest request, @RequestParam(value = "userId") UUID potentialFriendId) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseJson = mapper.createObjectNode();
        responseJson.put("success", false);
        responseJson.put("message", "");
        ObjectNode data = responseJson.putObject("data");

        User potentialFriend = this.userService.getUserById(potentialFriendId);

        if(potentialFriendId == null || potentialFriend == null) {
            responseJson.put("message", "Bad Data");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseJson);
        }
        User currentUser = (User) request.getSession().getAttribute("current_user");
        Set<UUID> userIds = new HashSet<UUID>();
        userIds.add(currentUser.getUserId());
        userIds.add(potentialFriendId);

        Conversation conversation = new Conversation();
        conversation.setConversationId(UUID.randomUUID());
        conversation.setCreateDate(java.time.Instant.now());
        conversation.setUpdateDate(java.time.Instant.now());
        conversation.setStatus(RecordStatus.ACTIVE);

        String name = currentUser.getUsername() + "|" + potentialFriend.getUsername();

        conversation.setName(name);
        conversation.setUsers(userIds);

        // this.conversationService.save(conversation);
        System.err.println(conversation.toString());
        responseJson.put("success", true);

        return ResponseEntity.status(HttpStatus.OK).body(responseJson);
    }

    @GetMapping(value = { "/test" } )
    public String test() {
        return "test";
    }
}
