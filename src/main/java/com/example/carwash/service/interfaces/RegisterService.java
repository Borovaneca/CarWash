package com.example.carwash.service.interfaces;

import com.example.carwash.model.dtos.UserRegisterDTO;
import com.example.carwash.model.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Consumer;

public interface RegisterService {

    public User registerUser(UserRegisterDTO userRegisterDTO, Consumer<Authentication> successfulLogin);
}
