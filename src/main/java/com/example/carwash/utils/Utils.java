package com.example.carwash.utils;

import com.example.carwash.model.dtos.AppointmentAddDTO;
import com.example.carwash.model.dtos.UserRegisterDTO;
import com.example.carwash.model.entity.Appointment;
import com.example.carwash.model.entity.User;
import com.example.carwash.model.view.AppointmentAwaitingApprovalView;
import com.example.carwash.model.view.MyAppointmentView;
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
//                    mapper.map(src -> src.getUser().getUsername(), AppointmentAwaitingApprovalView::setCreateBy);
//                    mapper.map(src -> {
//                        if (src.getCreateOn() == null) {
//                            return "";
//                        }
//                        LocalDateTime parse = LocalDateTime.parse(src.getCreateOn().toString(), DateTimeFormatter.ofPattern("dd.MM.yyyy/HH:mm"));
//                        return parse.format(DateTimeFormatter.ofPattern("dd.MM.yyyy/HH:mm"));
//
//                    }, AppointmentAwaitingApprovalView::setCreateOn);
//                    mapper.map(src -> {
//                        if (src.getMadeFor() == null) {
//                            return "";
//                        }
//                        LocalDateTime parse = LocalDateTime.parse(src.getMadeFor().toString(), DateTimeFormatter.ofPattern("dd.MM.yyyy/HH:mm"));
//                        return parse.format(DateTimeFormatter.ofPattern("dd.MM.yyyy/HH:mm"));
//                    }, AppointmentAwaitingApprovalView::setMadeFor);
//                    mapper.map(src ->  src.getVehicle() == null ? "" : src.getVehicle().getFullCarInfo(), AppointmentAwaitingApprovalView::setVehicle);
//                    mapper.map(src -> src.getService() == null ? "" : src.getService().getName(), AppointmentAwaitingApprovalView::setService);
//                    mapper.map(src -> src.getService() == null ? "" : "$" + src.getService().getPrice(), AppointmentAwaitingApprovalView::setPrice);
//                    mapper.map(src -> src.getId().toString(), AppointmentAwaitingApprovalView::setId);
//                });

        return modelMapper;
    }
}
