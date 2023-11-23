package com.example.carwash.service;

import com.example.carwash.model.entity.ConfirmationToken;
import com.example.carwash.model.entity.User;
import com.example.carwash.repository.ConfirmationTokenRepository;
import com.example.carwash.repository.UserRepository;
import com.example.carwash.service.interfaces.ConfirmationTokenService;
import com.example.carwash.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {


    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final UserService userService;

    @Autowired
    public ConfirmationTokenServiceImpl(ConfirmationTokenRepository confirmationTokenRepository, @Qualifier("userServiceProxy") UserService userService) {
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.userService = userService;
    }


    @Override
    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    @Override
    public ConfirmationToken findByToken(String token) {
        return confirmationTokenRepository.findByToken(token).orElse(null);
    }

    @Override
    public void confirmEmail(User user) {
        user.setActive(true);
        userService.save(user);
        confirmationTokenRepository.delete(confirmationTokenRepository.findByUserId(user.getId()).get());
    }
    @Override
    public void deleteToken(ConfirmationToken valid) {
        confirmationTokenRepository.delete(valid);
    }
}
