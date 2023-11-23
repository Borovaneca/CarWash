package com.example.carwash.service.interfaces;

import com.example.carwash.model.dtos.AppointmentServiceDTO;
import com.example.carwash.model.dtos.ServiceAddDTO;
import com.example.carwash.model.entity.Service;
import com.example.carwash.model.view.ServiceIndexView;
import com.example.carwash.model.view.ServiceView;

import java.util.List;

public interface ServiceService {

    List<AppointmentServiceDTO> getAllServices();

    List<ServiceIndexView> getAllServicesForIndexPage();

    List<ServiceView> getAllServicesForServicesPage();

    void addService(ServiceAddDTO serviceAddDTO);

    Service getByName(String name);

    int allServices();

    void saveAll(List<Service> services);

    void deleteServiceById(Long id);
}
