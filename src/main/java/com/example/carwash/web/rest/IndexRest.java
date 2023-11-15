package com.example.carwash.web.rest;

import com.example.carwash.model.view.ServiceIndexView;
import com.example.carwash.repository.ServiceRepository;
import com.example.carwash.service.interfaces.ServiceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/index")
public class IndexRest {

    private final ServiceService serviceService;

    @Autowired
    public IndexRest(ServiceService serviceService) {
        this.serviceService = serviceService;
    }


    @GetMapping("/")
    public ResponseEntity<List<ServiceIndexView>> getServices() {
        return ResponseEntity.ok(serviceService.getAllServicesForIndex());
    }
}
