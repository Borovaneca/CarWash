package com.example.carwash.web.rest;

import com.example.carwash.model.view.StaffView;
import com.example.carwash.service.interfaces.ViewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/about")
public class AboutRest {

    private final ViewService viewService;

    public AboutRest(ViewService viewService) {
        this.viewService = viewService;
    }


    @GetMapping("/")
    public ResponseEntity<List<StaffView>> getAboutStaff() {
        return ResponseEntity.ok(viewService.getAllStaffViews());
    }
}
