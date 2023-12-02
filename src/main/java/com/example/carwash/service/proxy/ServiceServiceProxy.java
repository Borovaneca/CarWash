package com.example.carwash.service.proxy;

import com.example.carwash.model.dtos.AppointmentServiceDTO;
import com.example.carwash.model.dtos.ServiceAddDTO;
import com.example.carwash.model.entity.Service;
import com.example.carwash.model.view.ServiceIndexView;
import com.example.carwash.model.view.ServiceView;
import com.example.carwash.service.ServiceServiceImpl;
import com.example.carwash.service.interfaces.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier("serviceServiceProxy")
public class ServiceServiceProxy implements ServiceService {

    private final ServiceServiceImpl serviceService;

    private List<AppointmentServiceDTO> appointmentServiceDTOS;
    private List<ServiceIndexView> serviceIndexViews;
    private List<ServiceView> serviceViews;


    @Autowired
    public ServiceServiceProxy(ServiceServiceImpl serviceService) {
        this.serviceService = serviceService;
    }

    @Override
    public List<AppointmentServiceDTO> getAllServicesForAppointmentPage() {
        if (appointmentServiceDTOS == null) {
            appointmentServiceDTOS = serviceService.getAllServicesForAppointmentPage();
        }
        return appointmentServiceDTOS;
    }

    @Override
    public List<ServiceIndexView> getAllServicesForIndexPage() {
        if (serviceIndexViews == null) {
            serviceIndexViews = serviceService.getAllServicesForIndexPage();
        }
        return serviceIndexViews;
    }

    @Override
    public List<ServiceView> getAllServicesForServicesPage() {
        if (serviceViews == null) {
            serviceViews = serviceService.getAllServicesForServicesPage();
        }
        return serviceViews;
    }

    @Override
    public void addService(ServiceAddDTO serviceAddDTO) {
        serviceService.addService(serviceAddDTO);

        appointmentServiceDTOS = null;
        serviceIndexViews = null;
        serviceViews = null;

        Thread thread = new Thread(() -> {
            getAllServicesForAppointmentPage();
            getAllServicesForIndexPage();
            getAllServicesForServicesPage();
        });
        thread.start();
    }

    @Override
    public Service getByName(String name) {
        return serviceService.getByName(name);
    }

    @Override
    public int allServices() {
        return serviceService.allServices();
    }

    @Override
    public void saveAll(List<Service> services) {
        serviceService.saveAll(services);

        appointmentServiceDTOS = null;
        serviceIndexViews = null;
        serviceViews = null;

        Thread thread = new Thread(() -> {
            getAllServicesForAppointmentPage();
            getAllServicesForIndexPage();
            getAllServicesForServicesPage();
        });
        thread.start();
    }

    @Override
    public void deleteServiceById(Long id) {
        serviceService.deleteServiceById(id);

        appointmentServiceDTOS = null;
        serviceIndexViews = null;
        serviceViews = null;

        Thread thread = new Thread(() -> {
            getAllServicesForAppointmentPage();
            getAllServicesForIndexPage();
            getAllServicesForServicesPage();
        });
        thread.start();
    }
}
