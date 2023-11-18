package com.example.carwash.mapper;

import com.example.carwash.model.entity.Appointment;
import com.example.carwash.model.view.AppointmentAwaitingApprovalView;
import com.example.carwash.model.view.AppointmentTodayView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CustomMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "madeFor",target = "madeFor", dateFormat = "dd.MM.yyyy/HH:mm"),
            @Mapping(source = "vehicle.fullCarInfo", target = "vehicle"),
            @Mapping(source = "service.name", target = "service"),
            @Mapping(source = "service.price", target = "price"),
            @Mapping(source = "user.username", target = "createBy"),
            @Mapping(source = "createOn",target = "createOn", dateFormat = "dd.MM.yyyy/HH:mm")
    })
    AppointmentAwaitingApprovalView AppointmentToAppointmentAwaitingApprovalView(Appointment appointment);

    @Mappings({
            @Mapping(source = "madeFor",target = "appointmentHour", dateFormat = "HH:mm"),
            @Mapping(source = "vehicle.fullCarInfo", target = "vehicle"),
            @Mapping(source = "service.name", target = "service"),
            @Mapping(source = "service.price", target = "price"),
            @Mapping(source = "user.username", target = "madeBy"),
            @Mapping(source = "createOn",target = "createOn", dateFormat = "dd.MM.yyyy/HH:mm")
    })
    AppointmentTodayView AppointmentToAppointmentTodayView(Appointment appointment);
}
