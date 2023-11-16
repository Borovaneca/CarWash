package com.example.carwash.service;

import com.example.carwash.model.dtos.AppointmentServiceDTO;
import com.example.carwash.model.view.ServiceIndexView;
import com.example.carwash.model.view.ServiceView;
import com.example.carwash.repository.ServiceRepository;
import com.example.carwash.service.interfaces.ServiceService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final ModelMapper modelMapper;

    public ServiceServiceImpl(ServiceRepository serviceRepository, ModelMapper modelMapper) {
        this.serviceRepository = serviceRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<AppointmentServiceDTO> getAllServices() {
        List<AppointmentServiceDTO> collect = serviceRepository.findAll()
                .stream()
                .map(entity -> modelMapper.map(entity, AppointmentServiceDTO.class))
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    @Cacheable("services")
    public List<ServiceIndexView> getAllServicesForIndex() {
        return serviceRepository
                .findAll()
                .stream()
                .map(entity -> modelMapper.map(entity, ServiceIndexView.class))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable("servicesForServices")
    public List<ServiceView> getAllServicesForServices() {
        System.out.println("HELLO WORLD!");
        return serviceRepository
                .findAll()
                .stream()
                .map(service -> modelMapper.map(service, ServiceView.class))
                .collect(Collectors.toList());
    }
}
