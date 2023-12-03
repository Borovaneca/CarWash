package com.example.carwash.service.interfaces;

import com.example.carwash.model.dtos.ProfileEditDTO;
import com.example.carwash.model.dtos.ProfileUpdateImageDTO;
import com.example.carwash.model.dtos.SocialMediaAddDTO;
import com.example.carwash.model.dtos.VehicleAddDTO;
import com.example.carwash.model.entity.User;
import com.example.carwash.model.view.AllUsersView;
import com.example.carwash.model.view.ProfileView;
import com.example.carwash.model.view.StaffView;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void update(ProfileEditDTO profileEditDTO);

    ProfileEditDTO getUserAndMapToProfileEditDTO(String username);

    User findByUsername(String username);

    void updateImage(ProfileUpdateImageDTO profileUpdateImageDTO);

    void addSocialMedia(String username, SocialMediaAddDTO socialMediaAddDTO);

    User findByEmail(String email);

    void deleteSocialMedia(String username, String socialName);

    void addVehicleToUser(String username, VehicleAddDTO vehicleAddDTO);

    boolean removeVehicleFromUser(String username, Long id);

    void save(User user);

    void addRoleToUserId(String role, Long userId);

    void removeRoleToUserId(String role, Long userId);

    void registerAdmin();

    Optional<List<User>> findInactiveUsersMoreThan7Days();

    void delete(User user);

    List<StaffView> getAllStaffViews();

    ProfileView getProfileView(String username);

    List<AllUsersView> getAllUsers();

    AllUsersView banOrUnbanUser(Long id);

    boolean isAuthorized(UserDetails userDetails, String username);

    boolean isAuthorized(UserDetails userDetails, Long userId);

    void deleteUser(String username);
}
