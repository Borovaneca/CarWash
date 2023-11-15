package com.example.carwash.service;

import com.example.carwash.model.entity.Appointment;
import com.example.carwash.model.entity.Role;
import com.example.carwash.model.entity.User;
import com.example.carwash.model.entity.Vehicle;
import com.example.carwash.model.enums.RoleName;
import com.example.carwash.model.view.*;
import com.example.carwash.repository.AppointmentRepository;
import com.example.carwash.repository.ServiceRepository;
import com.example.carwash.repository.UserRepository;
import com.example.carwash.repository.VehicleRepository;
import com.example.carwash.service.interfaces.ServiceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ViewService {

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final ServiceRepository serviceRepository;
    private final ServiceService serviceService;
    private final ModelMapper modelMapper;
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public ViewService(UserRepository userRepository, VehicleRepository vehicleRepository, ServiceRepository serviceRepository, ServiceService serviceService, ModelMapper modelMapper, AppointmentRepository appointmentRepository) {
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.serviceRepository = serviceRepository;
        this.serviceService = serviceService;
        this.modelMapper = modelMapper;
        this.appointmentRepository = appointmentRepository;
    }


    public ProfileView getProfileView(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return toProfileView(user);
    }

    private ProfileView toProfileView(User user) {
        ProfileView profileView = new ProfileView();
        profileView.setUsername(user.getUsername());
        profileView.setFirstName(user.getFirstName());
        profileView.setLastName(user.getLastName());
        profileView.setEmail(user.getEmail());
        profileView.setCity(user.getCity());
        profileView.setAge(user.getAge());
        profileView.setRole(getMajorRole(user.getRoles()));
        profileView.setLocatedOn(user.getImage().getLocatedOn());
        profileView.setVehicles(user.getVehicles().size());
        profileView.setRegisteredOn(user.getRegisteredOn());
        profileView.setAppointments(user.getAppointments().size());
        profileView.setBio(user.getBio());
        profileView.setActive(user.isActive());
        profileView.setSocials(getSocials(user));
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

    public List<StaffView> getAllStaffViews() {
        List<StaffView> views = userRepository.findAll()
                .stream()
                .filter(user -> user.getRoles().size() > 1)
                .map(this::toStaffView)
                .toList();
        return views;
    }

    private StaffView toStaffView(User user) {
        StaffView staff = new StaffView();
        staff.setFullName(user.getFullName());
        staff.setAge(user.getAge());
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

    public List<VehicleView> getVehiclesViewByUsername(String username) {
        Optional<List<Vehicle>> vehicles = vehicleRepository.findByUserUsername(username);
        return vehicles.map(vehicleList -> vehicleList.stream().map(this::toVehicleView).toList()).orElse(null);
    }

    private VehicleView toVehicleView(Vehicle vehicle) {
        VehicleView vehicleView = new VehicleView();
        vehicleView.setId(vehicle.getId());
        vehicleView.setBrand(vehicle.getBrand());
        vehicleView.setModel(vehicle.getModel());
        vehicleView.setColor(vehicle.getColor());
        return vehicleView;
    }


    public List<ServiceView> getServices() {
        return serviceService.getAllServicesForServices();
    }

    public List<MyAppointmentView> getMyAppointments(String username) {
        return appointmentRepository.findAllByUserUsername(username)
                .stream()
                .map(appointment -> modelMapper.map(appointment, MyAppointmentView.class))
                .peek(appointment -> {
                    LocalDateTime createOn = LocalDateTime.parse(appointment.getCreateOn());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy/HH:mm");
                    appointment.setCreateOn(createOn.format(formatter));})
                .peek(appointment -> {
                    LocalDateTime madeFor = LocalDateTime.parse(appointment.getMadeFor(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
                    appointment.setMadeFor(madeFor.format(DateTimeFormatter.ofPattern("dd.MM.yyyy/HH:mm")));})
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
}
