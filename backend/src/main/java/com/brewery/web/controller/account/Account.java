package com.brewery.web.controller.account;

import com.brewery.web.model.User;
import com.brewery.web.services.UserTableService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping(value = { "/account" })
public class Account {

    private final UserTableService accountService;

    public Account(UserTableService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = { "" })
    public String accountView() {
        return null;
    }

    @ResponseBody
    @PostMapping(value = { "/roles" })
    public ResponseEntity<ObjectNode> getRoles(HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseJson = mapper.createObjectNode();
        responseJson.put("success", false);
        responseJson.put("message", "");
        ArrayNode roleJson = responseJson.putArray("data");

        List<String> roles = this.accountService.getUsersRolesByUser((User) request.getSession().getAttribute("current_user"));

        for(String role : roles) {
            roleJson.add(role);
        }

        responseJson.put("success", true);

        return ResponseEntity.status(HttpStatus.OK).body(responseJson);
    }
}
