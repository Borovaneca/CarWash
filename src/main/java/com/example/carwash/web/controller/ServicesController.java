package com.example.carwash.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/services")
@Controller
public class ServicesController {


    @GetMapping("/view")
    public String get() {
        return "services";
    }
}
