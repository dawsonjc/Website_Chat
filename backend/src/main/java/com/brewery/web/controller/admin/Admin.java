package com.brewery.web.controller.admin;

import com.brewery.web.model.User;
import com.brewery.web.services.admin.AdminUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
@RequestMapping(value = { "/admin" })
public class Admin {

    @Autowired
    private AdminUserService adminUserService;

    @GetMapping(value = { "" })
    public String index(HttpServletRequest request) {
        request.setAttribute("unverified_users", this.adminUserService.getUnverifiedUsers());
        request.setAttribute("all_roles", this.adminUserService.getActiveRoles());
        return "admin/admin-index";
    }

    @ResponseBody
    @PostMapping(value = { "/user/verify" })
    public ResponseEntity<ObjectNode> verify(
            @RequestParam(value = "userId") UUID userId
    ) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseJson = mapper.createObjectNode();
        responseJson.put("success", false);
        responseJson.put("message", "");
        User user = this.adminUserService.getUserById(userId);

        user.setAccountVerificationStatus("Verified");

        this.adminUserService.saveUser(user);

        responseJson.put("success", true);

        responseJson.putPOJO("data", user);

        return ResponseEntity.status(HttpStatus.OK).body(responseJson);
    }

}
