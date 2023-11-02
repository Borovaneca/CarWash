package com.example.carwash.service;

import com.example.carwash.model.dtos.UserRegisterDTO;
import com.example.carwash.model.entity.User;
import com.example.carwash.model.enums.RoleName;
import com.example.carwash.repository.RoleRepository;
import com.example.carwash.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Consumer;

@Service
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;
    private SecurityContextRepository securityContextRepository;
    private final ProfileImageService profileImageService;
    private final RoleRepository roleRepository;

    public RegisterService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, SecurityContextRepository securityContextRepository, ProfileImageService profileImageService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.securityContextRepository = securityContextRepository;
        this.profileImageService = profileImageService;
        this.roleRepository = roleRepository;
    }


    public void registerUser(UserRegisterDTO userRegisterDTO, Consumer<Authentication> successfulLogin) {
        User user = mapToUser(userRegisterDTO);
        userRepository.save(user);

        var userDetails = userDetailsService.loadUserByUsername(userRegisterDTO.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        successfulLogin.accept(authentication);
    }

    private User mapToUser(UserRegisterDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setAge(null);
        user.setActive(true);
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
