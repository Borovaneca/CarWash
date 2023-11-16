package com.example.carwash.service.interfaces;

import com.example.carwash.model.dtos.ProfileEditDTO;
import com.example.carwash.model.dtos.ProfileUpdateImageDTO;
import com.example.carwash.model.dtos.SocialMediaAddDTO;
import com.example.carwash.model.dtos.VehicleAddDTO;
import com.example.carwash.model.entity.User;

public interface UserService {
    void update(ProfileEditDTO profileEditDTO);

    ProfileEditDTO getUserAndMapToProfileEditDTO(String username);

    User findByUsername(String username);

    void updateImage(ProfileUpdateImageDTO profileUpdateImageDTO);

    void addSocialMedia(String username, SocialMediaAddDTO socialMediaAddDTO);

    void confirmEmail(User user);

    User findByEmail(String email);

    void sendResetPasswordEmail(User user);

    void deleteSocialMedia(String username, String socialName);

    void addVehicleToUser(String username, VehicleAddDTO vehicleAddDTO);

    boolean removeVehicleFromUser(String username, Long id);

    void save(User user);

    void addRoleToUserId(String role, Long userId);

    void banUserById(Long userId);
}
