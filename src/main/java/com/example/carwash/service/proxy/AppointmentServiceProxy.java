package com.example.carwash.service.proxy;

import com.example.carwash.model.dtos.AppointmentAddDTO;
import com.example.carwash.model.entity.Appointment;
import com.example.carwash.model.view.AppointmentAwaitingApprovalView;
import com.example.carwash.model.view.AppointmentTodayView;
import com.example.carwash.model.view.MyAppointmentView;
import com.example.carwash.service.AppointmentServiceImpl;
import com.example.carwash.service.interfaces.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Qualifier("appointmentServiceProxy")
@Service
public class AppointmentServiceProxy implements AppointmentService {

    private final AppointmentServiceImpl appointmentService;

    private List<MyAppointmentView> myAppointmentViews;
    private List<AppointmentTodayView> appointmentTodayViews;
    private List<AppointmentAwaitingApprovalView> appointmentAwaitingApprovalViews;

    @Autowired
    public AppointmentServiceProxy(AppointmentServiceImpl appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Override
    public void addAppointmentToUserWithUsername(AppointmentAddDTO appointmentAddDTO, String username) {
        appointmentService.addAppointmentToUserWithUsername(appointmentAddDTO, username);

        Thread thread = new Thread(() -> {
            appointmentTodayViews = null;
            appointmentAwaitingApprovalViews = null;
            myAppointmentViews = null;

            findAllAppointmentsForToday();
            findAllAppointmentsWaitingApproval();
            getAppointmentsOfUser(username);

        });
        thread.start();
    }

    @Override
    public void approveAppointmentById(Long id) {
        appointmentService.approveAppointmentById(id);
    }

    @Override
    public void declineAppointmentById(Long id) {
        appointmentService.declineAppointmentById(id);
    }

    @Override
    public List<Appointment> findAllByStatus(int status) {
        return appointmentService.findAllByStatus(status);
    }

    @Override
    public void deleteAll(List<Appointment> rejected) {
        appointmentService.deleteAll(rejected);
        Thread thread = new Thread(() -> {
            appointmentTodayViews = null;
            appointmentAwaitingApprovalViews = null;
            myAppointmentViews = null;

            findAllAppointmentsForToday();
            findAllAppointmentsWaitingApproval();

        });
        thread.start();
    }

    @Override
    public List<AppointmentTodayView> findAllAppointmentsForToday() {
        if (appointmentTodayViews == null) {
            appointmentTodayViews = appointmentService.findAllAppointmentsForToday();
        }
        return appointmentTodayViews;
    }

    @Override
    public List<AppointmentAwaitingApprovalView> findAllAppointmentsWaitingApproval() {
        if (appointmentAwaitingApprovalViews == null) {
            appointmentAwaitingApprovalViews = appointmentService.findAllAppointmentsWaitingApproval();
        }
        return appointmentAwaitingApprovalViews;
    }

    @Override
    public List<MyAppointmentView> getAppointmentsOfUser(String username) {
        if (myAppointmentViews == null) {
            myAppointmentViews = appointmentService.getAppointmentsOfUser(username);
        }
        return myAppointmentViews;
    }
}

