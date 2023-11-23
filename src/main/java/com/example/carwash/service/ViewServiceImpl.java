package com.example.carwash.service;

import com.example.carwash.model.dtos.AppointmentServiceDTO;
import com.example.carwash.model.view.*;
import com.example.carwash.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViewServiceImpl implements ViewService {

    private final UserService userService;
    private final VehicleService vehicleService;
    private final ServiceService serviceService;
    private final AppointmentService appointmentService;

    @Autowired
    public ViewServiceImpl(@Qualifier("userServiceProxy") UserService userService, VehicleService vehicleService, @Qualifier("serviceServiceProxy") ServiceService serviceService, @Qualifier("appointmentServiceProxy") AppointmentService appointmentService) {
        this.userService = userService;
        this.vehicleService = vehicleService;
        this.serviceService = serviceService;
        this.appointmentService = appointmentService;
    }


    @Override
    public ProfileView getProfileView(String username) {
        return userService.getProfileView(username);
    }

    @Override
    public List<StaffView> getAllStaffViews() {
        return userService.getAllStaffViews();
    }

    @Override
    public List<VehicleView> getVehiclesViewByUsername(String username) {
        return vehicleService.getAllVehiclesByUserUsername(username);
    }


    @Override
    public List<ServiceView> getServices() {
        return serviceService.getAllServicesForServicesPage();
    }

    @Override
    public List<MyAppointmentView> getMyAppointments(String username) {
        return appointmentService.getAppointmentsOfUser(username);
    }

    @Override
    public List<AppointmentAwaitingApprovalView> getAwaitingApproval() {
        return appointmentService.findAllAppointmentsWaitingApproval();
    }

    @Override
    public List<AppointmentServiceDTO> getAllServices() {
        return serviceService.getAllServices();
    }

    @Override
    public List<VehicleView> getAllVehiclesByUserUsername(String username) {
    return vehicleService.getAllVehiclesByUserUsername(username);
    }

    @Override
    public List<AllUsersView> getAllUsers() {
        return userService.getAllUsers();
    }

    @Override
    public AllUsersView banOrUnbanUser(Long id) {
        return userService.banOrUnbanUser(id);
    }

    @Override
    public List<AppointmentTodayView> getAppointmentsForToday() {
        return appointmentService.findAllAppointmentsForToday();
    }
}