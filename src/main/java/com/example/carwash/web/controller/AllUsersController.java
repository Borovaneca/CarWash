package com.example.carwash.web.controller;

import com.example.carwash.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public AllUsersController(@Qualifier("userServiceProxy") UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users/all")
    public String allUsers(){
        return "owner/all-users";
    }

    @PostMapping("/users/add-role/{userId}/{role}")
    public String addRole(@PathVariable Long userId, @PathVariable String role){
        userService.addRoleToUserId(role,  userId);
        return "redirect:/owner/all-users";
    }

    @PostMapping("/users/remove-role/{userId}/{role}")
    public String removeRole(@PathVariable Long userId, @PathVariable String role){
        userService.removeRoleToUserId(role,  userId);
        return "redirect:/owner/all-users";
    }

}
