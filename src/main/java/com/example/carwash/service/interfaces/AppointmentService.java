package com.example.carwash.service.interfaces;

import com.example.carwash.model.dtos.AppointmentAddDTO;
import com.example.carwash.model.entity.Appointment;

import java.util.List;

public interface AppointmentService {


    void addAppointmentToUserWithUsername(AppointmentAddDTO appointmentAddDTO, String username);

    void approveAppointmentById(Long id);

    void declineAppointmentById(Long id);

    List<Appointment> findAllByStatus(int status);

    void deleteAll(List<Appointment> rejected);
}
