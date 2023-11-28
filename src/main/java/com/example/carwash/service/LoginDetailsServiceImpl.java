package com.example.carwash.service;

import com.example.carwash.model.entity.Role;
import com.example.carwash.model.entity.User;
import com.example.carwash.repository.UserRepository;
import com.example.carwash.service.interfaces.AppointmentService;
import com.example.carwash.service.interfaces.VehicleService;
import com.example.carwash.service.jwt.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.stream.Collectors;

public class LoginDetailsServiceImpl implements UserDetailsService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final HttpServletResponse response;
    private final VehicleService vehicleService;
    public LoginDetailsServiceImpl(VehicleService vehicleService, JwtService jwtService, UserRepository userRepository, HttpServletResponse response) {
        this.vehicleService = vehicleService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.response = response;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        UserDetails userDetails = user
                .map(LoginDetailsServiceImpl::map)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));
        Cookie cookie = new Cookie("jwt", jwtService.generateToken(user.get()));
        vehicleService.initVehiclesOfLoggInUser(username);
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return userDetails;
    }

    private static UserDetails map(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(LoginDetailsServiceImpl::map).collect(Collectors.toList())
        );
    }

    private static GrantedAuthority map(Role role) {
        return new SimpleGrantedAuthority(
                "ROLE_" + role.getName().name()
        );
    }
}
