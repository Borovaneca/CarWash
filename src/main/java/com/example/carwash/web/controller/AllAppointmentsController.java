package com.example.carwash.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AllAppointmentsController {



    @GetMapping("/manager/all-appointments")
    public String allAppointments(){
        return "manager/all-appointments";
    }
}
