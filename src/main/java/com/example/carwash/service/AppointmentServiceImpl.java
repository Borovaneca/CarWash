package com.example.carwash.service;

import com.example.carwash.model.dtos.AppointmentAddDTO;
import com.example.carwash.model.entity.Appointment;
import com.example.carwash.model.entity.User;
import com.example.carwash.repository.AppointmentRepository;
import com.example.carwash.repository.ServiceRepository;
import com.example.carwash.service.interfaces.AppointmentService;
import com.example.carwash.service.interfaces.ServiceService;
import com.example.carwash.service.interfaces.UserService;
import jakarta.persistence.Transient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final AppointmentRepository appointmentRepository;
    private final ServiceService serviceService;

    @Autowired
    public AppointmentServiceImpl(ModelMapper modelMapper, UserService userService, AppointmentRepository appointmentRepository, ServiceRepository serviceRepository, ServiceService serviceService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.appointmentRepository = appointmentRepository;
        this.serviceService = serviceService;
    }


    @Transient
    @Override
    public void addAppointmentToUserWithUsername(AppointmentAddDTO appointmentAddDTO, String username) {
        User user = userService.findByUsername(username);
        Appointment appointment = modelMapper.map(appointmentAddDTO, Appointment.class);
        appointment.setVehicle(user.getVehicles().stream().filter(vehicle -> vehicle.getId().equals(appointmentAddDTO.getVehicleId())).findAny().orElse(null));
        appointment.setId(null);
        appointment.setUser(user);
        appointment.setService(serviceService.getByName(appointmentAddDTO.getService()));
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
}
