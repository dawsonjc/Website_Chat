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
            RegisterFormData userData
    ) {
        System.err.println(userData.toString());
        if(this.userService.userExists(userData.email())) {
            return null;
        }
        // this.userService.register(userData);

        return "redirect:/account/login";
    }

    public record RegisterFormData(String firstName, String lastName, String username, String email, String password) {

        @Override
        public String toString() {
            return "RegisterFormData{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", username='" + username + '\'' +
                    ", email='" + email + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
}
