package com.example.carwash.service;

import com.example.carwash.errors.UserNotFoundException;
import com.example.carwash.model.dtos.ProfileEditDTO;
import com.example.carwash.model.dtos.ProfileUpdateImageDTO;
import com.example.carwash.model.dtos.SocialMediaAddDTO;
import com.example.carwash.model.dtos.VehicleAddDTO;
import com.example.carwash.model.entity.ResetPassword;
import com.example.carwash.model.entity.SocialMedia;
import com.example.carwash.model.entity.User;
import com.example.carwash.model.entity.Vehicle;
import com.example.carwash.events.events.ForgotPasswordEvent;
import com.example.carwash.model.enums.RoleName;
import com.example.carwash.repository.RoleRepository;
import com.example.carwash.repository.UserRepository;
import com.example.carwash.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SocialMediaServiceImpl socialMediaServiceImpl;
    private final PasswordEncoder passwordEncoder;
    private final ProfileImageServiceImpl profileImageServiceImpl;
    private final ResetServiceImpl resetServiceImpl;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final VehicleServiceImpl vehicleServiceImpl;
    private final RoleRepository roleRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, SocialMediaServiceImpl socialMediaServiceImpl, PasswordEncoder passwordEncoder, ProfileImageServiceImpl profileImageServiceImpl, ResetServiceImpl resetServiceImpl, ApplicationEventPublisher applicationEventPublisher, VehicleServiceImpl vehicleServiceImpl, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.socialMediaServiceImpl = socialMediaServiceImpl;
        this.passwordEncoder = passwordEncoder;
        this.profileImageServiceImpl = profileImageServiceImpl;
        this.resetServiceImpl = resetServiceImpl;
        this.applicationEventPublisher = applicationEventPublisher;
        this.vehicleServiceImpl = vehicleServiceImpl;
        this.roleRepository = roleRepository;
    }



    @Override
    public void update(ProfileEditDTO profileEditDTO) {
        userRepository.findById(profileEditDTO.getId()).ifPresent(user -> {
            user.setFirstName(profileEditDTO.getFirstName());
            user.setLastName(profileEditDTO.getLastName());
            user.setCity(profileEditDTO.getCity());
            user.setAge(profileEditDTO.getAge());
            user.setBio(profileEditDTO.getBio());
            user.setPassword(passwordEncoder.encode(profileEditDTO.getPassword()));
            userRepository.save(user);
        });
    }
    @Override
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

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public void updateImage(ProfileUpdateImageDTO profileUpdateImageDTO) {
        profileImageServiceImpl.saveProfileImage(profileUpdateImageDTO.getImage(), profileUpdateImageDTO.getUsername());
    }

    @Override
    public void addSocialMedia(String username, SocialMediaAddDTO socialMediaAddDTO) {
        userRepository
                .findByUsername(username)
                .ifPresent(
                        user -> {
                            user.getSocialMedias().add(socialMediaServiceImpl.addSocialMediaToUser(socialMediaAddDTO, user
                            ));
                            userRepository.save(user);
                        });
    }

    @Override
    public void confirmEmail(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
       return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public void sendResetPasswordEmail(User user) {
        ResetPassword reset = resetServiceImpl.makeTokenAndSaveIt(user);
        applicationEventPublisher.publishEvent(new ForgotPasswordEvent(user, user.getEmail(), reset.getToken(), user.getUsername()));
    }

    @Override
    public void deleteSocialMedia(String username, String socialName) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            SocialMedia media = socialMediaServiceImpl.getByNameAndUser(socialName, user);
            user.getSocialMedias().remove(media);
            userRepository.save(user);
            socialMediaServiceImpl.delete(media);
        }
    }

    private Vehicle mapToVehicleFromDTO(VehicleAddDTO vehicleAddDTO) {
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(vehicleAddDTO.getBrand());
        vehicle.setModel(vehicleAddDTO.getModel());
        vehicle.setColor(vehicleAddDTO.getColor());
        return vehicle;
    }

    @Override
    public void addVehicleToUser(String username, VehicleAddDTO vehicleAddDTO) {
        Vehicle vehicle = mapToVehicleFromDTO(vehicleAddDTO);
        User user = findByUsername(username);
        user.getVehicles().add(vehicle);
        vehicle.setUser(user);
        userRepository.save(user);
        vehicleServiceImpl.save(vehicle);

    }

    @Override
    public boolean removeVehicleFromUser(String username, Long id) {
        User user = findByUsername(username);
        Vehicle vehicle = vehicleServiceImpl.findById(id);
        if (vehicle == null) {
            return false;
        }

        user.getVehicles().remove(vehicle);
        vehicleServiceImpl.delete(vehicle);
        userRepository.save(user);
        return true;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void addRoleToUserId(String role, Long userId) {
        roleRepository.findByName(RoleName.valueOf(role)).ifPresent(ro -> {
            userRepository.findById(userId).ifPresent(user -> {
                if (user.getRoles().contains(ro)) return;
                user.getRoles().add(ro);
                userRepository.save(user);
            });
        });
    }

    @Override
    public void banUserById(Long userId) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setBanned(true);
            userRepository.save(user);
        });
    }
}
