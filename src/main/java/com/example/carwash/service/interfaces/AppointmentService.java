package com.example.carwash.service.interfaces;

import com.example.carwash.model.dtos.AppointmentAddDTO;
import com.example.carwash.model.entity.Appointment;
import com.example.carwash.model.view.AppointmentAwaitingApprovalView;
import com.example.carwash.model.view.AppointmentTodayView;
import com.example.carwash.model.view.MyAppointmentView;

import java.util.Collection;
import java.util.List;

public interface AppointmentService {


    void addAppointmentToUserWithUsername(AppointmentAddDTO appointmentAddDTO, String username);

    void approveAppointmentById(Long id);

    void declineAppointmentById(Long id);

    List<Appointment> findAllByStatus(int status);

    void deleteAll(List<Appointment> rejected);

    List<AppointmentTodayView> findAllAppointmentsForToday();

    List<AppointmentAwaitingApprovalView> findAllAppointmentsWaitingApproval();

    List<MyAppointmentView> getAppointmentsOfUser(String username);

    void refreshAppointments();

    List<Appointment> findByMadeForBeforeNow();
}
