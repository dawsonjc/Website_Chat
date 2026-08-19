package com.brewery.web.controller.account;

import com.brewery.web.services.UserTableService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = { "/account" })
public class Logout {

    private final UserTableService userService;

    public Logout(UserTableService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @PostMapping(value = { "/logout" })
    public ResponseEntity<ObjectNode> logout(HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseJson = mapper.createObjectNode();
        responseJson.put("success", false);
        responseJson.put("message", "");
        ObjectNode data = responseJson.putObject("data");

        HttpSession session = request.getSession(false);

        if(session != null) {
            session.invalidate();
        } else {
            System.err.println("Session is null");
            responseJson.put("message", "Session is null");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseJson);
        }

        responseJson.put("success", true);
        return ResponseEntity.status(HttpStatus.OK).body(responseJson);
    }

}
