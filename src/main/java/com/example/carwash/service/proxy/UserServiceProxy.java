package com.example.carwash.service.proxy;

import com.example.carwash.model.dtos.ProfileEditDTO;
import com.example.carwash.model.dtos.ProfileUpdateImageDTO;
import com.example.carwash.model.dtos.SocialMediaAddDTO;
import com.example.carwash.model.dtos.VehicleAddDTO;
import com.example.carwash.model.entity.User;
import com.example.carwash.model.view.AllUsersView;
import com.example.carwash.model.view.ProfileView;
import com.example.carwash.model.view.StaffView;
import com.example.carwash.service.UserServiceImpl;
import com.example.carwash.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
@Qualifier("userServiceProxy")
public class UserServiceProxy implements UserService {

    private final UserServiceImpl userService;

    private List<StaffView> staffViews;
    private List<AllUsersView> allUsers;

    @Autowired
    public UserServiceProxy(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public void update(ProfileEditDTO profileEditDTO) {
        userService.update(profileEditDTO);

        staffViews = null;
        allUsers = null;

        Thread thread = new Thread(() -> {
            staffViews = userService.getAllStaffViews();
            allUsers = userService.getAllUsers();
        });
        thread.start();
    }

    @Override
    public ProfileEditDTO getUserAndMapToProfileEditDTO(String username) {
        return userService.getUserAndMapToProfileEditDTO(username);
    }

    @Override
    public User findByUsername(String username) {
        return userService.findByUsername(username);
    }

    @Override
    public void updateImage(ProfileUpdateImageDTO profileUpdateImageDTO) {
        userService.updateImage(profileUpdateImageDTO);

        staffViews = null;
        allUsers = null;

        Thread thread = new Thread(() -> {
            staffViews = userService.getAllStaffViews();
            allUsers = userService.getAllUsers();
        });
        thread.start();
    }

    @Override
    public void addSocialMedia(String username, SocialMediaAddDTO socialMediaAddDTO) {
        userService.addSocialMedia(username, socialMediaAddDTO);

        staffViews = null;
        allUsers = null;

        Thread thread = new Thread(() -> {
            staffViews = userService.getAllStaffViews();
            allUsers = userService.getAllUsers();
        });
        thread.start();
    }

    @Override
    public User findByEmail(String email) {
        return userService.findByEmail(email);
    }

    @Override
    public void deleteSocialMedia(String username, String socialName) {
        userService.deleteSocialMedia(username, socialName);

        staffViews = null;
        allUsers = null;

        Thread thread = new Thread(() -> {
            staffViews = userService.getAllStaffViews();
            allUsers = userService.getAllUsers();
        });
        thread.start();
    }

    @Override
    public void addVehicleToUser(String username, VehicleAddDTO vehicleAddDTO) {
        userService.addVehicleToUser(username, vehicleAddDTO);

        staffViews = null;
        allUsers = null;

        Thread thread = new Thread(() -> {
            staffViews = userService.getAllStaffViews();
            allUsers = userService.getAllUsers();
        });
        thread.start();
    }

    @Override
    public boolean removeVehicleFromUser(String username, Long id) {
        boolean removed = userService.removeVehicleFromUser(username, id);

        staffViews = null;
        allUsers = null;

        Thread thread = new Thread(() -> {
            staffViews = userService.getAllStaffViews();
            allUsers = userService.getAllUsers();
        });
        thread.start();

        return removed;
    }

    @Override
    public void save(User user) {
        userService.save(user);

        allUsers = null;
        staffViews = null;

        Thread thread = new Thread(() -> {
            allUsers = userService.getAllUsers();
            staffViews = userService.getAllStaffViews();
        });
        thread.start();
    }

    @Override
    public void addRoleToUserId(String role, Long userId) {
        userService.addRoleToUserId(role, userId);
        allUsers = null;
        staffViews = null;

        Thread thread = new Thread(() -> {
            allUsers = userService.getAllUsers();
            staffViews = userService.getAllStaffViews();
        });
        thread.start();
    }

    @Override
    public void removeRoleToUserId(String role, Long userId) {
        userService.removeRoleToUserId(role, userId);

        allUsers = null;
        staffViews = null;

        Thread thread = new Thread(() -> {
            allUsers = userService.getAllUsers();
            staffViews = userService.getAllStaffViews();
        });
        thread.start();
    }

    @Override
    public void registerAdmin() {
        userService.registerAdmin();

        allUsers = null;
        staffViews = null;

        Thread thread = new Thread(() -> {
            allUsers = userService.getAllUsers();
            staffViews = userService.getAllStaffViews();
        });
        thread.start();
    }

    @Override
    public Optional<List<User>> findInactiveUsersMoreThan7Days() {
        return userService.findInactiveUsersMoreThan7Days();
    }

    @Override
    public void delete(User user) {
        userService.delete(user);

        allUsers = null;
        staffViews = null;

        Thread thread = new Thread(() -> {
            allUsers = userService.getAllUsers();
            staffViews = userService.getAllStaffViews();
        });
        thread.start();
    }

    @Override
    public List<StaffView> getAllStaffViews() {
        if (staffViews == null) {
            staffViews = userService.getAllStaffViews();
        }
        return staffViews;
    }

    @Override
    public ProfileView getProfileView(String username) {
        return userService.getProfileView(username);
    }

    @Override
    public List<AllUsersView> getAllUsers() {
        if (allUsers == null) {
            allUsers = userService.getAllUsers();
        }
        return allUsers;
    }

    @Override
    public AllUsersView banOrUnbanUser(Long id) {
        AllUsersView allUsersView = userService.banOrUnbanUser(id);

        allUsers = null;
        staffViews = null;

        Thread thread = new Thread(() -> {
            allUsers = userService.getAllUsers();
            staffViews = userService.getAllStaffViews();
        });
        thread.start();
        return allUsersView;
    }

    @Override
    public boolean isAuthorized(UserDetails userDetails, String username) {
        return userService.isAuthorized(userDetails, username);
    }

    @Override
    public boolean isAuthorized(UserDetails userDetails, Long userId) {
        return userService.isAuthorized(userDetails, userId);
    }

    @Override
    public void deleteUser(String username) {
        userService.deleteUser(username);

        allUsers = null;
        staffViews = null;

        Thread thread = new Thread(() -> {
            allUsers = userService.getAllUsers();
            staffViews = userService.getAllStaffViews();
        });
        thread.start();
    }
}
