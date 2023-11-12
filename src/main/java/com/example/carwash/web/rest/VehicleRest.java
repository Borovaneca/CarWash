package com.example.carwash.web.rest;

import com.example.carwash.model.view.VehicleView;
import com.example.carwash.service.ViewService;
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
@RequestMapping("/api/my-vehicles")
public class VehicleRest {

    private final ViewService viewService;

    @Autowired
    public VehicleRest(ViewService viewService) {
        this.viewService = viewService;
    }


    @GetMapping("/")
    public ResponseEntity<List<VehicleView>> getVehicles(@AuthenticationPrincipal UserDetails userDetails) {
        List<VehicleView> vehicleViews = viewService.getVehiclesViewByUsername(userDetails.getUsername());
        return ResponseEntity.ok(vehicleViews);
    }
}
