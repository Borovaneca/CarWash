package com.example.carwash.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppointmentsToday {



    @GetMapping("/employee/appointments-today")
    public String getAllAppointmentsForToday() {
        return "employee/appointments-today";
    }
}
