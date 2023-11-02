package com.example.carwash.web.controller;

import com.example.carwash.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@CrossOrigin("*")
@Controller
public class ProfileController {

    private final ViewService viewService;

    @Autowired
    public ProfileController(ViewService viewService) {
        this.viewService = viewService;
    }


    @GetMapping("/users/profile/{username}")
    @CrossOrigin("*")
    public String getProfile(@PathVariable String username, Model model, Principal principal) {
        model.addAttribute("user", viewService.getProfileView(username));
        if (principal.getName().equals(username)) {
            model.addAttribute("isOwner", true);
        }
        return "profile";
    }
}
