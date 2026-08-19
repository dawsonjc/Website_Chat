package com.brewery.web.controller.account;

import com.brewery.web.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = { "/account" })
public class ForgotPassword {
    // TODO: Emails


    @GetMapping(value = { "/forgot-password" })
    public String forgetPasswordView(HttpServletRequest request) {
        request.setAttribute("user", new User());
        return "account/forgot-password";
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgetPassword() {
        return ResponseEntity.status(501).body((new UnsupportedOperationException()).getMessage());
    }
}
