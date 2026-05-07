package com.brewery.web.controller.account;

import com.brewery.web.model.User;
import com.brewery.web.services.UserTableService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = { "/account" })
public class Login {

    @Autowired
    private UserTableService userService;

    @GetMapping(value = { "/login" })
    public String loginView(Model model, HttpServletRequest request) {
        model.addAttribute("new_user", new User());
        model.addAttribute("login_user", new User());
        return "account/login";
    }

    @ResponseBody
    @PostMapping(value = { "/login" }, consumes = { "application/json" }, produces = {  "application/json" })
    public ResponseEntity<ObjectNode> login(
            HttpServletRequest request,
            @RequestBody JsonNode formUser
    ) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode respJson = mapper.createObjectNode();
        respJson.put("success", false);
        respJson.put("message", "");
        ObjectNode data = respJson.putObject("data");

        if(!formUser.has("username") || !formUser.has("password")) {
            return ResponseEntity.status(400).body(respJson);
        }

        if(!this.userService.userExists(formUser.get("username").asText())) {
            return ResponseEntity.status(401).body(respJson);
        }

        UUID userId = this.userService.getUserIdByEmail(formUser.get("username").asText());
        User user = this.userService.getUserByIdAndPassword(userId, formUser.get("password").asText());

        if(user == null) {
            return ResponseEntity.status(401).body(respJson);
        }
        user.setLastLoginDate(Instant.now());
        user.setApiAuthToken(UUID.randomUUID());
        this.userService.save(user); // last login date

        respJson.put("success", true);

        data.put("userId", userId.toString());
        data.put("username", user.getUsername());
        ArrayNode rolesJsonArray = data.putArray("roles");
        List<String> roles = user.getRoles();
        for(String role : roles) {
            rolesJsonArray.add(role);
        }

        request.getSession().setAttribute("current_user", user);

        return ResponseEntity.status(HttpStatus.OK).body(respJson);
    }
}
