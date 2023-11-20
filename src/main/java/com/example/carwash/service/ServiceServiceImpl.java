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
        return serviceRepository.findAll()
                .stream()
                .map(entity -> modelMapper.map(entity, AppointmentServiceDTO.class))
                .collect(Collectors.toList());
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
        return serviceRepository
                .findAll()
                .stream()
                .map(service -> modelMapper.map(service, ServiceView.class))
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
