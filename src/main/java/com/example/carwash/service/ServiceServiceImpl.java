package com.example.carwash.service;

import com.example.carwash.mapper.CustomMapper;
import com.example.carwash.model.dtos.AppointmentServiceDTO;
import com.example.carwash.model.dtos.ServiceAddDTO;
import com.example.carwash.model.view.ServiceIndexView;
import com.example.carwash.model.view.ServiceView;
import com.example.carwash.repository.ServiceRepository;
import com.example.carwash.service.interfaces.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final CustomMapper customMapper;

    @Autowired
    public ServiceServiceImpl(ServiceRepository serviceRepository, CustomMapper customMapper) {
        this.serviceRepository = serviceRepository;
        this.customMapper = customMapper;
    }


    @Override
    public List<AppointmentServiceDTO> getAllServicesForAppointmentPage() {
        return serviceRepository.findAll()
                .stream()
                .map(customMapper::appointmentToAppointmentServiceDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceIndexView> getAllServicesForIndexPage() {
        return serviceRepository
                .findAll()
                .stream()
                .map(customMapper::serviceToServiceIndexView)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceView> getAllServicesForServicesPage() {
        return serviceRepository
                .findAll()
                .stream()
                .map(customMapper::serviceToServiceView)
                .collect(Collectors.toList());
    }

    @Override
    public void addService(ServiceAddDTO serviceAddDTO) {
        com.example.carwash.model.entity.Service service = customMapper.serviceAddDTOToService(serviceAddDTO);
        serviceRepository.save(service);
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

    @Override
    public void deleteServiceById(Long id) {
        Optional<com.example.carwash.model.entity.Service> service = serviceRepository.findById(id);
        if (service.isPresent()) {
            service.get().getAppointments()
                    .forEach(appointment -> {
                        appointment.setService(null);
                    });
            serviceRepository.delete(service.get());
        }
    }
}
