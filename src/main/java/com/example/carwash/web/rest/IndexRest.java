package com.example.carwash.web.rest;

import com.example.carwash.model.view.ServiceIndexView;
import com.example.carwash.repository.ServiceRepository;
import org.modelmapper.ModelMapper;
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

    private final ServiceRepository serviceRepository;
    private final ModelMapper modelMapper;

    public IndexRest(ServiceRepository serviceRepository, ModelMapper modelMapper) {
        this.serviceRepository = serviceRepository;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/")
    public ResponseEntity<List<ServiceIndexView>> getServices() {
        return ResponseEntity.ok(serviceRepository
                .findAll()
                .stream()
                .map(entity -> modelMapper.map(entity, ServiceIndexView.class))
                .collect(Collectors.toList()));
    }
}
