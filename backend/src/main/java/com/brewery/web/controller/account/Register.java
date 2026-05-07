package com.brewery.web.controller.account;

import com.brewery.web.model.User;
import com.brewery.web.services.UserTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @PostMapping(value = { "/register" })
    public String register(
            @ModelAttribute("register_user") User userData
    ) {
        if(this.userService.userExists(userData.getEmail())) {
            return null;
        }
        this.userService.register(userData);

        return "redirect:/account/login";
    }
}
