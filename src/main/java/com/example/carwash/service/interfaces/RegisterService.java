package com.example.carwash.service.interfaces;

import com.example.carwash.model.dtos.UserRegisterDTO;
import org.springframework.security.core.Authentication;

import java.util.function.Consumer;

public interface RegisterService {

    public void registerUser(UserRegisterDTO userRegisterDTO, Consumer<Authentication> successfulLogin);
}
