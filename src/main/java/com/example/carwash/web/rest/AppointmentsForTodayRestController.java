package com.example.carwash.web.rest;

import com.example.carwash.model.view.AppointmentTodayView;
import com.example.carwash.service.interfaces.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/appointments/today")
public class AppointmentsForTodayRestController {

    private final ViewService viewService;

    @Autowired
    public AppointmentsForTodayRestController(ViewService viewService) {
        this.viewService = viewService;
    }


    @CrossOrigin("*")
    @GetMapping
    public ResponseEntity<List<AppointmentTodayView>> getAppointmentsForToday() {
        return ResponseEntity.ok(viewService.getAppointmentsForToday());
    }
}
