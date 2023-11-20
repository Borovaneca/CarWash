package com.example.carwash.aop.initDB;

import com.example.carwash.model.entity.*;
import com.example.carwash.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SeedDataBase {


    private final UserService userService;


    @Autowired
    public SeedDataBase(UserService userService) {
        this.userService = userService;
    }

    public void registerAdmin() {
    userService.registerAdmin();
    }
}
