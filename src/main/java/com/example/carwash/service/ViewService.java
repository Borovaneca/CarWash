package com.example.carwash.service;

import com.example.carwash.model.entity.Role;
import com.example.carwash.model.entity.User;
import com.example.carwash.model.entity.Vehicle;
import com.example.carwash.model.enums.RoleName;
import com.example.carwash.model.view.ProfileView;
import com.example.carwash.model.view.SocialMediaView;
import com.example.carwash.model.view.StaffView;
import com.example.carwash.model.view.VehicleView;
import com.example.carwash.repository.UserRepository;
import com.example.carwash.repository.VehicleRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ViewService {

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;

    public ViewService(UserRepository userRepository, VehicleRepository vehicleRepository) {
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
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
}
