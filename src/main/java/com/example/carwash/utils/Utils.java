package com.example.carwash.utils;

import com.example.carwash.model.dtos.UserRegisterDTO;
import com.example.carwash.model.entity.User;
import com.example.carwash.model.enums.RoleName;
import com.example.carwash.repository.RoleRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;

@Component
public class Utils {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public Utils(RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
