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

    private List<AppointmentTodayView> appointmentTodayViews;
    private List<AppointmentAwaitingApprovalView> appointmentAwaitingApprovalViews;
    private List<MyAppointmentView> myAppointments;
    private String username;

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
            myAppointments = null;
            findAllAppointmentsForToday();
            findAllAppointmentsWaitingApproval();
            getAppointmentsOfUser(username);
        });
        thread.start();
    }

    @Override
    public void approveAppointmentById(Long id) {
        appointmentService.approveAppointmentById(id);

        appointmentTodayViews = null;
        appointmentAwaitingApprovalViews = null;
        myAppointments = null;
        Thread thread = new Thread(() -> {

            findAllAppointmentsForToday();
            findAllAppointmentsWaitingApproval();
            getAppointmentsOfUser(username);
        });
        thread.start();
    }

    @Override
    public void declineAppointmentById(Long id) {
        appointmentService.declineAppointmentById(id);

        appointmentTodayViews = null;
        appointmentAwaitingApprovalViews = null;
        myAppointments = null;
        Thread thread = new Thread(() -> {

            findAllAppointmentsForToday();
            findAllAppointmentsWaitingApproval();
            getAppointmentsOfUser(username);
        });
        thread.start();
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
            myAppointments = null;
            findAllAppointmentsForToday();
            findAllAppointmentsWaitingApproval();
            getAppointmentsOfUser(username);
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
        if (myAppointments == null) {
            myAppointments = appointmentService.getAppointmentsOfUser(username);
        }
        return myAppointments;
    }

    @Override
    public List<Appointment> findByMadeForBeforeNow() {
        return appointmentService.findByMadeForBeforeNow();
    }

    @Override
    public void refreshAllAppointments() {
        appointmentTodayViews = null;
        appointmentAwaitingApprovalViews = null;
        myAppointments = null;
        Thread thread = new Thread(() -> {
            findAllAppointmentsForToday();
            findAllAppointmentsWaitingApproval();
            if (username != null) {
                getAppointmentsOfUser(username);
            }
        });
        thread.start();
    }
}

