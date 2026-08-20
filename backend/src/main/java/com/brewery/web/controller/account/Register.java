package com.brewery.web.controller.account;

import com.brewery.web.dto.formdata.RegisterFormData;
import com.brewery.web.model.User;
import com.brewery.web.services.UserTableService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = { "/account" })
public class Register {

    @Autowired
    private UserTableService userService;

    @RequestMapping(value = { "/register" })
    public String registerView(Model model) {
        model.addAttribute("register_user", new User());
        return "account/register";
    }

    @ResponseBody
    @PostMapping(value = { "/register" })
    public ResponseEntity<ObjectNode> register(
            RegisterFormData userData
    ) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseJson = mapper.createObjectNode();
        responseJson.put("success", false);
        responseJson.put("message", "");
        ObjectNode data = responseJson.putObject("data");

        if(userData.email() == null || this.userService.userExists(userData.email())) {
            responseJson.put("message", "User already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseJson);
        }

        this.userService.register(userData);

        responseJson.put("success", true);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);
    }

}
