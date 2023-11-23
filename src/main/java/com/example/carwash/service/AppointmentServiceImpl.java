package com.example.carwash.service;

import com.example.carwash.mapper.CustomMapper;
import com.example.carwash.model.dtos.AppointmentAddDTO;
import com.example.carwash.model.entity.Appointment;
import com.example.carwash.model.entity.User;
import com.example.carwash.model.view.AppointmentAwaitingApprovalView;
import com.example.carwash.model.view.AppointmentTodayView;
import com.example.carwash.model.view.MyAppointmentView;
import com.example.carwash.repository.AppointmentRepository;
import com.example.carwash.service.interfaces.AppointmentService;
import com.example.carwash.service.interfaces.ServiceService;
import com.example.carwash.service.interfaces.UserService;
import jakarta.persistence.Transient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final UserService userService;
    private final AppointmentRepository appointmentRepository;
    private final ServiceService serviceService;
    private final CustomMapper customMapper;

    @Autowired
    public AppointmentServiceImpl(@Qualifier("userServiceProxy") UserService userService, AppointmentRepository appointmentRepository, @Qualifier("serviceServiceProxy") ServiceService serviceService, CustomMapper customMapper) {
        this.userService = userService;
        this.appointmentRepository = appointmentRepository;
        this.serviceService = serviceService;
        this.customMapper = customMapper;
    }


    @Transient
    @Override
    public void addAppointmentToUserWithUsername(AppointmentAddDTO appointmentAddDTO, String username) {
        User user = userService.findByUsername(username);
        Appointment appointment = customMapper.appointmentAddDTOToAppointment(appointmentAddDTO);
        appointment.setVehicle(user.getVehicles().stream().filter(vehicle -> vehicle.getId().equals(appointmentAddDTO.getVehicleId())).findAny().orElse(null));
        appointment.setUser(user);
        com.example.carwash.model.entity.Service service = serviceService.getByName(appointmentAddDTO.getService());
        service.getAppointments().add(appointment);
        appointment.setService(service);
        appointmentRepository.save(appointment);
        user.getAppointments().add(appointment);
        userService.save(user);
    }

    @Override
    public void approveAppointmentById(Long id) {
        appointmentRepository.findById(id).ifPresent(appointment -> {
            appointment.setStatus(1);
            appointmentRepository.save(appointment);
        });
    }

    @Override
    public void declineAppointmentById(Long id) {
        appointmentRepository.findById(id).ifPresent(appointment -> {
            appointment.setStatus(-1);
            appointmentRepository.save(appointment);
        });
    }

    @Override
    public List<Appointment> findAllByStatus(int status) {
        return appointmentRepository.findAllByStatus(status);
    }

    @Override
    public void deleteAll(List<Appointment> rejected) {
        appointmentRepository.deleteAll(rejected);
    }

    @Override
    public List<AppointmentTodayView> findAllAppointmentsForToday() {
        return appointmentRepository.findAllAppointmentsForToday().stream()
                .map(customMapper::appointmentToAppointmentTodayView)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentAwaitingApprovalView> findAllAppointmentsWaitingApproval() {
        return appointmentRepository.findAllByStatus(0).stream()
                .map(customMapper::appointmentToAppointmentAwaitingApprovalView)
                .collect(Collectors.toList());
    }

    @Override
    public List<MyAppointmentView> getAppointmentsOfUser(String username) {
        return appointmentRepository.findAllByUserUsername(username)
                .stream()
                .map(customMapper::appointmentToMyAppointmentView)
                .collect(Collectors.toList());
    }
}
