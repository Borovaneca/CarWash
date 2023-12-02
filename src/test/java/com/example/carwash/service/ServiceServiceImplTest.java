package com.example.carwash.service;

import com.example.carwash.mapper.CustomMapper;
import com.example.carwash.model.dtos.AppointmentServiceDTO;
import com.example.carwash.model.entity.Service;
import com.example.carwash.model.view.ServiceIndexView;
import com.example.carwash.model.view.ServiceView;
import com.example.carwash.repository.ServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ServiceServiceImplTest {

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private CustomMapper customMapper;

    @InjectMocks
    private ServiceServiceImpl serviceService;

    private List<Service> services;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        services = List.of(
                new Service("TestName", "TestDescription", "https://www.google.com", 10.00, new ArrayList<>()),
                new Service("TestName2", "TestDescription2", "https://www.google.com", 10.00, new ArrayList<>()),
                new Service("TestName3", "TestDescription3", "https://www.google.com", 10.00, new ArrayList<>()),
                new Service("TestName4", "TestDescription4", "https://www.google.com", 10.00, new ArrayList<>()));
    }

    @Test
    public void testGetAllServices() {
        serviceRepository.saveAll(services);
        when(serviceRepository.findAll()).thenReturn(services);

        assertEquals(4, serviceService.allServices());
    }

    @Test
    public void testGetServiceByName() {
        Service service = services.get(0);
        serviceRepository.save(service);
        when(serviceRepository.getByName(service.getName())).thenReturn(service);

        assertEquals(service, serviceService.getByName(service.getName()));
    }

    @Test
    public void getAllServicesForServicesPage() {
        List<ServiceView> serviceViews = services.stream()
                .map(customMapper::serviceToServiceView)
                .collect(Collectors.toList());
        when(serviceRepository.findAll()).thenReturn(services);
        when(customMapper.serviceToServiceView(any(Service.class))).thenReturn(serviceViews.get(0));

        assertEquals(serviceViews, serviceService.getAllServicesForServicesPage());
    }

    @Test
    public void getAllServicesForIndexPage() {
        List<ServiceIndexView> serviceViews = services.stream()
                .map(customMapper::serviceToServiceIndexView)
                .collect(Collectors.toList());
        when(serviceRepository.findAll()).thenReturn(services);
        when(customMapper.serviceToServiceIndexView(any(Service.class))).thenReturn(serviceViews.get(0));

        assertEquals(serviceViews, serviceService.getAllServicesForIndexPage());
    }

    @Test
    public void getAllServicesForAppointmentPage() {
        List<AppointmentServiceDTO> appointmentServiceDTOS = services.stream()
                .map(customMapper::appointmentToAppointmentServiceDTO)
                .collect(Collectors.toList());
        when(serviceRepository.findAll()).thenReturn(services);
        when(customMapper.appointmentToAppointmentServiceDTO(any(Service.class))).thenReturn(appointmentServiceDTOS.get(0));

        assertEquals(appointmentServiceDTOS, serviceService.getAllServicesForAppointmentPage());
    }

    @Test
    public void testAddService() {
        Service service = services.get(0);
        when(customMapper.serviceAddDTOToService(any())).thenReturn(service);
        when(serviceRepository.save(service)).thenReturn(service);
        when(serviceRepository.getByName(service.getName())).thenReturn(service);

        assertEquals(service, serviceService.getByName(service.getName()));
    }

    @Test
    public void testSaveAll() {
        when(serviceRepository.saveAll(services)).thenReturn(services);
        serviceService.saveAll(services);
        when(serviceRepository.findAll()).thenReturn(services);

        assertEquals(services.size(), serviceService.allServices());
    }

    @Test
    public void testDeleteServiceById() {
        Service service = services.get(0);
        serviceRepository.save(service);
        when(serviceRepository.findById(service.getId())).thenReturn(Optional.of(service));
        serviceService.deleteServiceById(service.getId());
        when(serviceRepository.findAll()).thenReturn(services);
    }
}
