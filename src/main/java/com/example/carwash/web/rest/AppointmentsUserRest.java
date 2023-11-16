package com.example.carwash.web.rest;

import com.example.carwash.model.view.MyAppointmentView;
import com.example.carwash.service.interfaces.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/appointments")
public class AppointmentsUserRest {

    private final ViewService viewService;

    @Autowired
    public AppointmentsUserRest(ViewService viewService) {
        this.viewService = viewService;
    }


    @GetMapping("/")
    public ResponseEntity<List<MyAppointmentView>> getAppointments(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(viewService.getMyAppointments(userDetails.getUsername()));
    }
}
