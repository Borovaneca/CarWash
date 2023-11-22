package com.example.carwash.mapper;

import com.example.carwash.model.dtos.AppointmentAddDTO;
import com.example.carwash.model.dtos.UserRegisterDTO;
import com.example.carwash.model.entity.Appointment;
import com.example.carwash.model.entity.SocialMedia;
import com.example.carwash.model.entity.User;
import com.example.carwash.model.view.*;
import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Mapper(componentModel = "spring", imports = {LocalDate.class, ArrayList.class, LocalDateTime.class})
public interface CustomMapper {


    @Mappings({
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "image.locatedOn", target = "locatedOn"),
            @Mapping(source = "age", target = "age"),
            @Mapping(source = "registeredOn", target = "registeredOn", dateFormat = "dd.MM.yyyy"),
            @Mapping(source = "active", target = "active"),
            @Mapping(source = "firstName", target = "firstName"),
            @Mapping(source = "lastName", target = "lastName"),
            @Mapping(source = "city", target = "city"),
            @Mapping(target = "vehicles", expression = "java(user.getVehicles().size())"),
            @Mapping(target = "appointments", expression = "java(user.getAppointments().size())"),
            @Mapping(source = "bio", target = "bio")
    })
    ProfileView userToProfileView(User user);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "madeFor",target = "madeFor", dateFormat = "dd.MM.yyyy/HH:mm"),
            @Mapping(source = "vehicle.fullCarInfo", target = "vehicle"),
            @Mapping(source = "service.name", target = "service"),
            @Mapping(source = "service.price", target = "price"),
            @Mapping(source = "user.username", target = "createBy"),
            @Mapping(source = "createOn",target = "createOn", dateFormat = "dd.MM.yyyy/HH:mm")
    })
    AppointmentAwaitingApprovalView appointmentToAppointmentAwaitingApprovalView(Appointment appointment);

    @Mappings({
            @Mapping(source = "madeFor",target = "appointmentHour", dateFormat = "HH:mm"),
            @Mapping(source = "vehicle.fullCarInfo", target = "vehicle"),
            @Mapping(source = "service.name", target = "service"),
            @Mapping(source = "service.price", target = "price"),
            @Mapping(source = "user.username", target = "madeBy"),
            @Mapping(source = "createOn",target = "createOn", dateFormat = "dd.MM.yyyy/HH:mm")
    })
    AppointmentTodayView appointmentToAppointmentTodayView(Appointment appointment);


    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "image.locatedOn", target = "locatedOn"),
            @Mapping(source = "age", target = "age"),
            @Mapping(source = "registeredOn", target = "registeredOn", dateFormat = "dd.MM.yyyy"),
            @Mapping(source = "banned", target = "isBanned")
    })
    AllUsersView userToAllUsersView(User user);

    @Mappings({
            @Mapping(source = "type", target = "type"),
            @Mapping(source = "link", target = "link")
    })
    SocialMediaView socialToSocialMediaView(SocialMedia social);

    @Mappings({
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "image.locatedOn", target = "image"),
            @Mapping(source = "age", target = "age"),
    })
    StaffView userToStaffView(User user);

    @Mappings({
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "email", target = "email"),
            @Mapping(target = "password", expression = "java(null)"),
            @Mapping(target = "firstName", expression = "java(null)"),
            @Mapping(target = "lastName", expression = "java(null)"),
            @Mapping(target = "city", expression = "java(null)"),
            @Mapping(target = "age", expression = "java(null)"),
            @Mapping(target = "active", expression = "java(false)"),
            @Mapping(target = "banned", expression = "java(false)"),
            @Mapping(target = "roles", expression = "java(new ArrayList<>())"),
            @Mapping(target = "vehicles", expression = "java(new ArrayList<>())"),
            @Mapping(target = "appointments", expression = "java(new ArrayList<>())"),
            @Mapping(target = "registeredOn", expression = "java(LocalDate.now())"),
            @Mapping(target = "image", expression = "java(null)"),
            @Mapping(target = "bio", expression = "java(null)")
    })
    User userRegistrationDTOToUser(UserRegisterDTO userRegisterDTO);

    @Mappings({
            @Mapping(target = "madeFor", expression = "java(appointmentAddDTO.getMadeFor())"),
            @Mapping(target = "createOn", expression = "java(LocalDateTime.now())"),
            @Mapping(target = "status", expression = "java(0)"),
            @Mapping(target = "service", expression = "java(null)"),
            @Mapping(target = "vehicle", expression = "java(null)"),
            @Mapping(target = "user", expression = "java(null)"),
            @Mapping(target = "id", expression = "java(null)")
    })
    Appointment appointmentAddDTOToAppointment(AppointmentAddDTO appointmentAddDTO);

    @Mappings({
            @Mapping(target = "createOn", expression = "java(appointment.getCreateOn().format(DateTimeFormatter.ofPattern(\"dd.MM.yyyy/HH:mm\")))"),
            @Mapping(target = "madeFor", expression = "java(appointment.getMadeFor().format(DateTimeFormatter.ofPattern(\"yyyy-MM-dd/HH:mm\")))"),
            @Mapping(target = "service", expression = "java(appointment.getService().getName())"),
            @Mapping(target = "vehicle", expression = "java(appointment.getVehicle().getFullCarInfo())"),
            @Mapping(target = "status", expression = "java(appointment.getStatus() == 0 ? \"PENDING\" : appointment.getStatus() == 1 ? \"APPROVED\" : \"REJECTED\")"),
            @Mapping(target = "price", expression = "java(appointment.getService().getPrice().toString())"),
    })
    MyAppointmentView appointmentToMyAppointmentView(Appointment appointment);
    @Condition
    default String isBanned(boolean banned) {
        return banned ? "Yes" : "No";
    }
}
