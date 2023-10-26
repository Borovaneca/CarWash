package com.example.carwash.service;

import com.example.carwash.model.entity.Role;
import com.example.carwash.model.entity.User;
import com.example.carwash.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class LoginDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public LoginDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(LoginDetailsService::map)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
    }

    private static UserDetails map(User user) {
    return org.springframework.security.core.userdetails.User
            .withUsername(user.getUsername())
            .password(user.getPassword())
            .authorities(user.getRoles().stream().map(LoginDetailsService::map).toList())
            .build();
    }

    private static GrantedAuthority map(Role role) {
    return new SimpleGrantedAuthority(
            "ROLE_" + role.getName().name()
    );
    }
}
