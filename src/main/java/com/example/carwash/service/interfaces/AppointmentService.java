package com.example.carwash.service.interfaces;

import com.example.carwash.model.dtos.AppointmentAddDTO;

public interface AppointmentService {


    void addAppointmentToUserWithUsername(AppointmentAddDTO appointmentAddDTO, String username);

    void approveAppointmentById(Long id);

    void declineAppointmentById(Long id);
}
