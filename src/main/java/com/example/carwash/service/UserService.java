package com.example.carwash.service;

import com.example.carwash.errors.UserNotFoundException;
import com.example.carwash.model.dtos.ProfileEditDTO;
import com.example.carwash.model.dtos.ProfileUpdateImageDTO;
import com.example.carwash.model.dtos.SocialMediaAddDTO;
import com.example.carwash.model.entity.ResetPassword;
import com.example.carwash.model.entity.SocialMedia;
import com.example.carwash.model.entity.User;
import com.example.carwash.model.events.ForgotPasswordEvent;
import com.example.carwash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final SocialMediaService socialMediaService;
    private final PasswordEncoder passwordEncoder;
    private final ProfileImageService profileImageService;
    private final ResetService resetService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public UserService(UserRepository userRepository, SocialMediaService socialMediaService, PasswordEncoder passwordEncoder, ProfileImageService profileImageService, ResetService resetService, ApplicationEventPublisher applicationEventPublisher) {
        this.userRepository = userRepository;
        this.socialMediaService = socialMediaService;
        this.passwordEncoder = passwordEncoder;
        this.profileImageService = profileImageService;
        this.resetService = resetService;
        this.applicationEventPublisher = applicationEventPublisher;
    }



    public void update(ProfileEditDTO profileEditDTO) {
        User user = userRepository.findById(profileEditDTO.getId()).get();

        user.setFirstName(profileEditDTO.getFirstName());
        user.setLastName(profileEditDTO.getLastName());
        user.setCity(profileEditDTO.getCity());
        user.setAge(profileEditDTO.getAge());
        user.setBio(profileEditDTO.getBio());
        user.setPassword(passwordEncoder.encode(profileEditDTO.getPassword()));
        userRepository.save(user);
    }
    public ProfileEditDTO getUserAndMapToProfileEditDTO(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(this::mapToProfileEditDTO).orElseThrow(() -> new UserNotFoundException("Username: " + username + " not found", username));
    }

    private ProfileEditDTO mapToProfileEditDTO(User user) {
        ProfileEditDTO profileEditDTO = new ProfileEditDTO();
        profileEditDTO.setId(user.getId());
        profileEditDTO.setUsername(user.getUsername());
        profileEditDTO.setPassword(null);
        profileEditDTO.setConfirmPassword(null);
        profileEditDTO.setEmail(user.getEmail());
        profileEditDTO.setFirstName(user.getFirstName());
        profileEditDTO.setLastName(user.getLastName());
        profileEditDTO.setCity(user.getCity());
        profileEditDTO.setAge(user.getAge());
        profileEditDTO.setBio(user.getBio());
        return profileEditDTO;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public void updateImage(ProfileUpdateImageDTO profileUpdateImageDTO) {
        profileImageService.saveProfileImage(profileUpdateImageDTO.getImage(), profileUpdateImageDTO.getUsername());
    }

    public void addSocialMedia(String username, SocialMediaAddDTO socialMediaAddDTO) {
        userRepository
                .findByUsername(username)
                .ifPresent(
                        user -> {
                            user.getSocialMedias().add(socialMediaService.addSocialMediaToUser(socialMediaAddDTO, user
                            ));
                            userRepository.save(user);
                        });
    }

    public void confirmEmail(User user) {
        userRepository.save(user);
    }

    public User findByEmail(String email) {
       return userRepository.findByEmail(email).orElse(null);
    }

    public void sendResetPasswordEmail(User user) {
        ResetPassword reset = resetService.makeTokenAndSaveIt(user);
        applicationEventPublisher.publishEvent(new ForgotPasswordEvent(user, user.getEmail(), reset.getToken(), user.getUsername()));
    }

    public void deleteSocialMedia(String username, String socialName) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            SocialMedia media = socialMediaService.getByNameAndUser(socialName, user);
            user.getSocialMedias().remove(media);
            userRepository.save(user);
            socialMediaService.delete(media);
        }
    }
}
