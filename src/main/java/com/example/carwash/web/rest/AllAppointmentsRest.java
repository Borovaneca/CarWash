package com.example.carwash.web.rest;

import com.example.carwash.service.interfaces.AppointmentService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AllAppointmentsRest {

    private final AppointmentService appointmentService;

    public AllAppointmentsRest(@Qualifier("appointmentServiceProxy") AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }


    @GetMapping("/api/all-appointments")
    public ResponseEntity<?> getAllAppointments() {
        return ResponseEntity.status(200).body(appointmentService.findAllAppointments());
    }
}
