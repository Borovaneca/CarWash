package com.example.carwash.web.rest;

import com.example.carwash.model.view.ServiceView;
import com.example.carwash.repository.ServiceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/services")
public class ServicesRest {

    private final ServiceRepository serviceRepository;
    private final ModelMapper modelMapper;

    public ServicesRest(ServiceRepository serviceRepository, ModelMapper modelMapper) {
        this.serviceRepository = serviceRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public ResponseEntity<List<ServiceView>> getServices() {
        return ResponseEntity.ok(serviceRepository
                .findAll()
                .stream()
                .map(service -> modelMapper.map(service, ServiceView.class))
                .collect(Collectors.toList()));
    }

}
