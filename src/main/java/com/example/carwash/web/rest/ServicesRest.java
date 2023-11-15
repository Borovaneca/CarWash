package com.example.carwash.web.rest;

import com.example.carwash.model.view.ServiceView;
import com.example.carwash.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/services")
public class ServicesRest {

    private final ViewService viewService;

    @Autowired
    public ServicesRest(ViewService viewService) {
        this.viewService = viewService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ServiceView>> getServices() {
        return ResponseEntity.ok(viewService.getServices());
    }

}
