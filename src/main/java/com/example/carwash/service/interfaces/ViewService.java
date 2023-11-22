package com.example.carwash.service.interfaces;

import com.example.carwash.model.dtos.AppointmentServiceDTO;
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

    List<VehicleView> getAllVehiclesByUserUsername(String username);

    List<AllUsersView> getAllUsers();

    AllUsersView banOrUnbanUser(Long id);

    List<AppointmentTodayView> getAppointmentsForToday();
}
