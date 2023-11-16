package com.example.carwash.service.interfaces;

import com.example.carwash.model.dtos.AppointmentServiceDTO;
import com.example.carwash.model.dtos.AppointmentVehicleDTO;
import com.example.carwash.model.view.*;

import java.util.List;

public interface ViewService {
    ProfileView getProfileView(String username);

    List<StaffView> getAllStaffViews();

    List<VehicleView> getVehiclesViewByUsername(String username);

    List<ServiceView> getServices();

    List<MyAppointmentView> getMyAppointments(String username);

    List<AppointmentAwaitingApprovalView> getAwaitingApproval();

    List<AppointmentServiceDTO> getAllServices();

    List<AppointmentVehicleDTO> getAllVehiclesByUserUsername(String username);

    List<AllUsersView> getAllUsers();

    AllUsersView banUser(Long id);

    List<AppointmentTodayView> getAppointmentsForToday();
}
