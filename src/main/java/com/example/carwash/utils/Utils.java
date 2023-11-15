package com.example.carwash.utils;

import com.example.carwash.model.dtos.AppointmentAddDTO;
import com.example.carwash.model.dtos.UserRegisterDTO;
import com.example.carwash.model.entity.Appointment;
import com.example.carwash.model.entity.User;
import com.example.carwash.model.view.AppointmentAwaitingApprovalView;
import com.example.carwash.model.view.MyAppointmentView;
import com.example.carwash.model.view.ProfileView;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Component
public class Utils {


    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(UserRegisterDTO.class, User.class)
                .addMappings(mapper -> {
                    mapper.map(UserRegisterDTO::getUsername, User::setUsername);
                    mapper.map(UserRegisterDTO::getPassword, User::setPassword);
                    mapper.map(UserRegisterDTO::getEmail, User::setEmail);
                    mapper.map(src -> null, User::setFirstName);
                    mapper.map(src -> null, User::setLastName);
                    mapper.map(src -> null, User::setCity);
                    mapper.map(src -> null, User::setAge);
                    mapper.map(src -> false, User::setActive);
                    mapper.map(src -> false, User::setBanned);
                    mapper.map(src -> new ArrayList<>(), User::setRoles);
                    mapper.map(src -> new ArrayList<>(), User::setVehicles);
                    mapper.map(src -> new ArrayList<>(), User::setAppointments);
                    mapper.map(src -> LocalDate.now(), User::setRegisteredOn);
                    mapper.map(src -> null, User::setImage);
                });

        modelMapper.typeMap(AppointmentAddDTO.class, Appointment.class)
                .addMappings(mapper -> {
                    mapper.map(AppointmentAddDTO::getMadeFor, Appointment::setMadeFor);
                    mapper.map(src -> LocalDateTime.now(), Appointment::setCreateOn);
                    mapper.map(src -> false, Appointment::setExpired);
                    mapper.map(src -> 0, Appointment::setStatus);
                });

        modelMapper.typeMap(Appointment.class, MyAppointmentView.class)
                .addMappings(mapper -> {
                   mapper.map(Appointment::getCreateOn, MyAppointmentView::setCreateOn);
                   mapper.map(Appointment::getMadeFor, MyAppointmentView::setMadeFor);
                   mapper.map(Appointment::getStatus, MyAppointmentView::setStatus);
                   mapper.map(src -> src.getService().getPrice(), MyAppointmentView::setPrice);
                   mapper.map(src -> src.getService().getName(),  MyAppointmentView::setService);
                   mapper.map(src -> src.getVehicle().getFullCarInfo(), MyAppointmentView::setVehicle);
                });

//        modelMapper.typeMap(Appointment.class, AppointmentAwaitingApprovalView.class)
//                .addMappings(mapper -> {
//                   mapper.map(Appointment::getCreateOn, AppointmentAwaitingApprovalView::setCreateOn);
//                   mapper.map(Appointment::getMadeFor, AppointmentAwaitingApprovalView::setMadeFor);
//                   mapper.map(src -> src.getService().getName() == null ? "" : src.getService().getName(), AppointmentAwaitingApprovalView::setService);
//                    mapper.map(src -> src.getService().getPrice() == null ? "" : src.getService().getPrice(), AppointmentAwaitingApprovalView::setPrice);
//                    mapper.map(src -> src.getVehicle().getFullCarInfo() == null ? "" : src.getVehicle().getFullCarInfo(), AppointmentAwaitingApprovalView::setVehicle);
//                   mapper.map(src -> src.getVehicle().getId() == null ? "" : src.getVehicle().getId(), AppointmentAwaitingApprovalView::setVehicle);
//                   mapper.map(src -> src.getUser().getUsername() == null ? "" : src.getUser().getUsername(), AppointmentAwaitingApprovalView::setCreateBy);
//                });

        return modelMapper;
    }
}
