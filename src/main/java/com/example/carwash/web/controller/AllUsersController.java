package com.example.carwash.web.controller;

import com.example.carwash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/owner")
public class AllUsersController {

    private final UserService userService;

    @Autowired
    public AllUsersController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users/all")
    public String allUsers(){
        return "owner/all-users";
    }

    @PostMapping("/users/add-role/{userId}/{role}")
    public String allUsersPost(@PathVariable Long userId, @PathVariable String role){
        userService.addRoleToUserId(role,  userId);
        return "owner/all-users";
    }

}
