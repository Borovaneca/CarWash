package com.example.carwash.service;

import com.example.carwash.model.dtos.AppointmentServiceDTO;
import com.example.carwash.model.dtos.AppointmentVehicleDTO;
import com.example.carwash.model.entity.Appointment;
import com.example.carwash.model.entity.Role;
import com.example.carwash.model.entity.User;
import com.example.carwash.model.enums.RoleName;
import com.example.carwash.model.view.*;
import com.example.carwash.repository.AppointmentRepository;
import com.example.carwash.repository.UserRepository;
import com.example.carwash.service.interfaces.ServiceService;
import com.example.carwash.service.interfaces.ViewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ViewServiceImpl implements ViewService {

    private final UserRepository userRepository;
    private final VehicleServiceImpl vehicleServiceImpl;
    private final ServiceService serviceService;
    private final ModelMapper modelMapper;
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public ViewServiceImpl(UserRepository userRepository, VehicleServiceImpl vehicleServiceImpl, ServiceService serviceService, ModelMapper modelMapper, AppointmentRepository appointmentRepository) {
        this.userRepository = userRepository;
        this.vehicleServiceImpl = vehicleServiceImpl;
        this.serviceService = serviceService;
        this.modelMapper = modelMapper;
        this.appointmentRepository = appointmentRepository;
    }


    @Override
    public ProfileView getProfileView(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return toProfileView(user);
    }

    private ProfileView toProfileView(User user) {
        ProfileView profileView = modelMapper.map(user, ProfileView.class);
        profileView.setSocials(user.getSocialMedias().stream().map(social -> modelMapper.map(social, SocialMediaView.class))
                        .collect(Collectors.toSet()));
        profileView.setRole(getMajorRole(user.getRoles()));
        profileView.setLocatedOn(user.getImage().getLocatedOn());
        profileView.setVehicles(String.valueOf(user.getVehicles().size()));
        profileView.setAppointments(String.valueOf(user.getAppointments().size()));
        return profileView;
    }

    private Set<SocialMediaView> getSocials(User user) {
        return user.getSocialMedias().stream().map(social -> {
            SocialMediaView socialMediaView = new SocialMediaView();
            socialMediaView.setType(social.getType());
            socialMediaView.setLink(social.getLink());
            return socialMediaView;
        }).collect(Collectors.toSet());
    }

    @Override
    public List<StaffView> getAllStaffViews() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRoles().size() > 1)
                .map(this::toStaffView)
                .toList();
    }

    private StaffView toStaffView(User user) {
        StaffView staff = modelMapper.map(user, StaffView.class);
        staff.setPosition(getMajorRole(user.getRoles()));
        staff.setImage(user.getImage().getLocatedOn());
        return staff;
    }

    private String getMajorRole(List<Role> roles) {
        Map<RoleName, Integer> rolePriorityMap = Map.of(
                RoleName.USER, 0,
                RoleName.EMPLOYEE, 1,
                RoleName.MANAGER, 2,
                RoleName.OWNER, 3
        );
        Role majorRole = roles.get(0);
        for (Role role : roles) {
            if (rolePriorityMap.get(role.getName()) > rolePriorityMap.get(majorRole.getName())) {
                majorRole = role;
            }
        }
        return majorRole.getName().name();
    }

    @Override
    public List<VehicleView> getVehiclesViewByUsername(String username) {
        return vehicleServiceImpl.getVehiclesViewByUsernameAndGetVehicleView(username);
    }


    @Override
    public List<ServiceView> getServices() {
        return serviceService.getAllServicesForServices();
    }

    @Override
    public List<MyAppointmentView> getMyAppointments(String username) {
        return appointmentRepository.findAllByUserUsername(username)
                .stream()
                .map(appointment -> modelMapper.map(appointment, MyAppointmentView.class))
                .peek(appointment -> {
                    LocalDateTime createOn = LocalDateTime.parse(appointment.getCreateOn());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy/HH:mm");
                    appointment.setCreateOn(createOn.format(formatter));
                })
                .peek(appointment -> {
                    LocalDateTime madeFor = LocalDateTime.parse(appointment.getMadeFor(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
                    appointment.setMadeFor(madeFor.format(DateTimeFormatter.ofPattern("dd.MM.yyyy/HH:mm")));
                })
                .peek(appointment -> {
                    appointment.setStatus(switch (appointment.getStatus()) {
                        case "1" -> "APPROVED";
                        case "0" -> "PENDING";
                        case "-1" -> "REJECTED";
                        default -> "Unknown";
                    });
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentAwaitingApprovalView> getAwaitingApproval() {
        return appointmentRepository.findAllByStatus(0)
                .stream()
                .map(this::toAppointmentAwaitingApprovalView)
                .collect(Collectors.toList());
    }

    private AppointmentAwaitingApprovalView toAppointmentAwaitingApprovalView(Appointment appointment) {
        AppointmentAwaitingApprovalView appointmentAwaitingApprovalView = new AppointmentAwaitingApprovalView();
        appointmentAwaitingApprovalView.setCreateBy(appointment.getUser().getUsername());
        appointmentAwaitingApprovalView.setCreateOn(DateTimeFormatter.ofPattern("dd.MM.yyyy/HH:mm").format(appointment.getCreateOn()));
        appointmentAwaitingApprovalView.setMadeFor(DateTimeFormatter.ofPattern("dd.MM.yyyy/HH:mm").format(appointment.getMadeFor()));
        appointmentAwaitingApprovalView.setVehicle(appointment.getVehicle().getFullCarInfo());
        appointmentAwaitingApprovalView.setService(appointment.getService().getName());
        appointmentAwaitingApprovalView.setPrice("$" + appointment.getService().getPrice());
        appointmentAwaitingApprovalView.setId(appointment.getId().toString());
        return appointmentAwaitingApprovalView;
    }

    @Override
    public List<AppointmentServiceDTO> getAllServices() {
        return serviceService.getAllServices();
    }

    @Override
    public List<AppointmentVehicleDTO> getAllVehiclesByUserUsername(String username) {
    return vehicleServiceImpl.getAllVehiclesByUserUsername(username);
    }

    @Override
    public List<AllUsersView> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(u -> {
                    AllUsersView userView = modelMapper.map(u, AllUsersView.class);
                    userView.setIsBanned(u.isBanned() ? "Yes" : "No");
                    userView.setLocatedOn(u.getImage().getLocatedOn());
                    userView.setRole(getMajorRole(u.getRoles()));
                    return userView;
                })
                .collect(Collectors.toList());
    }

    @Override
    public AllUsersView banUser(Long id) {
        return userRepository.findById(id).map(user -> {
            user.setBanned(true);
            userRepository.save(user);
            AllUsersView allUsersView = modelMapper.map(user, AllUsersView.class);
            allUsersView.setIsBanned(user.isBanned() ? "true" : "false");
            allUsersView.setLocatedOn(user.getImage().getLocatedOn());
            allUsersView.setRole(getMajorRole(user.getRoles()));
            return allUsersView;
        }).orElse(null);
    }

    @Override
    public List<AppointmentTodayView> getAppointmentsForToday() {
        List<AppointmentTodayView> views = appointmentRepository.findAllAppointmentsForToday()
                .stream()
                .map(this::toAppointmentTodayView)
                .collect(Collectors.toList());
        return views;
    }

    private AppointmentTodayView toAppointmentTodayView(Appointment appointment) {
        AppointmentTodayView view = new AppointmentTodayView();
        view.setVehicle(appointment.getVehicle().getFullCarInfo());
        view.setPrice(appointment.getService().getPrice().toString());
        view.setMadeBy(appointment.getUser().getUsername());
        view.setService(appointment.getService().getName());
        view.setCreateOn(DateTimeFormatter.ofPattern("dd.MM.yyyy").format(appointment.getCreateOn()));
        return view;
    }
}