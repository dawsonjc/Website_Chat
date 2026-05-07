package com.brewery.web.controller;

import com.brewery.web.model.Conversation;
import com.brewery.web.services.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Controller
public class Index {

    @Autowired
    private ConversationService conversationService;


    @GetMapping(value = { "/" })
    public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "redirect:/conversations?conversationId=" + this.conversationService.getGlobalChat().conversationId();
    }
}
