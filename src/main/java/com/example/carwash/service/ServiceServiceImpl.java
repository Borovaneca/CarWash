package com.example.carwash.service;

import com.example.carwash.mapper.CustomMapper;
import com.example.carwash.model.dtos.AppointmentServiceDTO;
import com.example.carwash.model.view.ServiceIndexView;
import com.example.carwash.model.view.ServiceView;
import com.example.carwash.repository.ServiceRepository;
import com.example.carwash.service.interfaces.ServiceService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final CustomMapper customMapper;

    public ServiceServiceImpl(ServiceRepository serviceRepository, CustomMapper customMapper) {
        this.serviceRepository = serviceRepository;
        this.customMapper = customMapper;
    }


    @Override
    public List<AppointmentServiceDTO> getAllServices() {
        return serviceRepository.findAll()
                .stream()
                .map(customMapper::appointmentToAppointmentServiceDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable("services")
    public List<ServiceIndexView> getAllServicesForIndex() {
        return serviceRepository
                .findAll()
                .stream()
                .map(customMapper::serviceToServiceIndexView)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable("servicesForServices")
    public List<ServiceView> getAllServicesForServices() {
        return serviceRepository
                .findAll()
                .stream()
                .map(customMapper::serviceToServiceView)
                .collect(Collectors.toList());
    }

    @Override
    public com.example.carwash.model.entity.Service getByName(String name) {
        return serviceRepository.getByName(name);
    }

    @Override
    public int allServices() {
        return serviceRepository.findAll().size();
    }

    @Override
    public void saveAll(List<com.example.carwash.model.entity.Service> services) {
        serviceRepository.saveAll(services);
    }
}
