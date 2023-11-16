package com.example.carwash.web.rest;

import com.example.carwash.model.view.AppointmentAwaitingApprovalView;
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
@RequestMapping("/api/awaiting-approval")
public class AppointmentAwaitingApprovalRestController {

    private final ViewService viewService;

    @Autowired
    public AppointmentAwaitingApprovalRestController(ViewService viewService) {
        this.viewService = viewService;
    }

    @GetMapping("/")
    public ResponseEntity<List<AppointmentAwaitingApprovalView>> getAwaitingApproval() {
        return ResponseEntity.ok(viewService.getAwaitingApproval());
    }
}
