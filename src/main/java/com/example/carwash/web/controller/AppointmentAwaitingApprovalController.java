package com.example.carwash.web.controller;

import com.example.carwash.service.interfaces.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager/awaiting-approval")
public class AppointmentAwaitingApprovalController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentAwaitingApprovalController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }


    @GetMapping()
    public String awaitingApproval(){
        return "manager/awaiting-approval";
    }


    @PostMapping("/approve/{id}")
    public String approveAppointment(@PathVariable Long id){
        appointmentService.approveAppointmentById(id);
        return "redirect:/manager/awaiting-approval";
    }

    @PostMapping("/decline/{id}")
    public String declineAppointment(@PathVariable Long id){
        appointmentService.declineAppointmentById(id);
        return "redirect:/manager/awaiting-approval";
    }
}
