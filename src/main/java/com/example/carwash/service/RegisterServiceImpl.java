package com.example.carwash.service;

import com.example.carwash.mapper.CustomMapper;
import com.example.carwash.model.dtos.UserRegisterDTO;
import com.example.carwash.model.entity.ConfirmationToken;
import com.example.carwash.model.entity.User;
import com.example.carwash.model.enums.RoleName;
import com.example.carwash.events.events.UserRegisteredEvent;
import com.example.carwash.repository.UserRepository;
import com.example.carwash.service.interfaces.ConfirmationTokenService;
import com.example.carwash.service.interfaces.ProfileImageService;
import com.example.carwash.service.interfaces.RegisterService;
import com.example.carwash.service.interfaces.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

@Service
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final CustomMapper customMapper;
    private final ProfileImageService profileImageService;
    private final RoleService roleService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ConfirmationTokenService confirmationTokenService;

    @Autowired
    public RegisterServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                               UserDetailsService userDetailsService,
                               CustomMapper customMapper, ProfileImageService profileImageService, RoleService roleService,
                               ApplicationEventPublisher applicationEventPublisher,
                               ConfirmationTokenService confirmationTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.customMapper = customMapper;
        this.profileImageService = profileImageService;
        this.roleService = roleService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.confirmationTokenService = confirmationTokenService;
    }


    @Override
    public User registerUser(UserRegisterDTO userRegisterDTO, Consumer<Authentication> successfulLogin) {
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
        return user;
    }

    private User mapToUser(UserRegisterDTO dto) {
//        User user = modelMapper.map(dto, User.class);
        User user = customMapper.userRegistrationDTOToUser(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.getRoles().add(roleService.findByName(RoleName.USER));
        if (dto.getImage() == null || Objects.equals(dto.getImage().getOriginalFilename(), "")) {
            user.setImage(profileImageService.getDefaultProfileImage());
            return user;
        }
        user.setImage(profileImageService.saveProfileImage(dto.getImage(), user));
        return user;
    }
}
