package com.example.carwash.aop.initDB;

import com.example.carwash.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartSeedDB implements CommandLineRunner {

    private final UserService userService;

    @Autowired
    public StartSeedDB(@Qualifier("userServiceProxy") UserService userService) {
        this.userService = userService;
    }


    @Override
    public void run(String... args) throws Exception {
        if (userService.findByUsername("admin") == null) {
            userService.registerAdmin();
        }
    }
}
