package com.example.carwash.service;

import com.example.carwash.model.dtos.UserRegisterDTO;
import com.example.carwash.model.entity.ConfirmationToken;
import com.example.carwash.model.entity.User;
import com.example.carwash.model.enums.RoleName;
import com.example.carwash.model.events.UserRegisteredEvent;
import com.example.carwash.repository.RoleRepository;
import com.example.carwash.repository.UserRepository;
import com.example.carwash.service.interfaces.ConfirmationTokenService;
import com.example.carwash.service.interfaces.RegisterService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Consumer;

@Service
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final ProfileImageService profileImageService;
    private final RoleRepository roleRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ConfirmationTokenService confirmationTokenService;

    public RegisterServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, ProfileImageService profileImageService, RoleRepository roleRepository, ApplicationEventPublisher applicationEventPublisher, ConfirmationTokenService confirmationTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.profileImageService = profileImageService;
        this.roleRepository = roleRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.confirmationTokenService = confirmationTokenService;
    }


    @Override
    public void registerUser(UserRegisterDTO userRegisterDTO, Consumer<Authentication> successfulLogin) {
        User user = mapToUser(userRegisterDTO);
        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                user
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        var userDetails = userDetailsService.loadUserByUsername(userRegisterDTO.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        successfulLogin.accept(authentication);
        applicationEventPublisher.publishEvent(new UserRegisteredEvent(
                "UserService", userRegisterDTO.getEmail(), userRegisterDTO.getUsername(), token));
    }

    private User mapToUser(UserRegisterDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setAge(null);
        user.setActive(false);
        user.setRoles(new ArrayList<>());
        user.getRoles().add(roleRepository.findByName(RoleName.USER).get());
        user.setCity(null);
        user.setFirstName(null);
        user.setLastName(null);
        user.setRegisteredOn(LocalDate.now());
        user.setVehicles(new ArrayList<>());
        user.setAppointments(new ArrayList<>());
        user.setImage(profileImageService.saveProfileImage(dto.getImage(), dto.getUsername()));
        return user;
    }
}
