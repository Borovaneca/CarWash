package com.example.carwash.service;

import com.example.carwash.model.entity.Role;
import com.example.carwash.model.entity.User;
import com.example.carwash.model.enums.RoleName;
import com.example.carwash.model.view.ProfileView;
import com.example.carwash.model.view.StaffView;
import com.example.carwash.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ViewService {

    private UserRepository userRepository;

    public ViewService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        return profileView;
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
}
