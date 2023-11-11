package com.example.carwash.service;

import com.example.carwash.model.entity.Role;
import com.example.carwash.model.entity.User;
import com.example.carwash.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;

public class LoginDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public LoginDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(LoginDetailsServiceImpl::map)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
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
