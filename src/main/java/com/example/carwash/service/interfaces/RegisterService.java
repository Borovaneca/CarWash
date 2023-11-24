package com.example.carwash.service.interfaces;

import com.example.carwash.model.dtos.RegisterDTO;
import org.springframework.security.core.Authentication;

import java.util.function.Consumer;

public interface RegisterService {

    public com.example.carwash.model.entity.User registerUser(RegisterDTO registerDTO, Consumer<Authentication> successfulLogin);
}
