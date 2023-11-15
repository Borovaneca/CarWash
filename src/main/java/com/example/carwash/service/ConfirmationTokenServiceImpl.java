package com.example.carwash.service;

import com.example.carwash.constants.ExceptionMessages;
import com.example.carwash.errors.TokenNotFoundException;
import com.example.carwash.model.entity.ConfirmationToken;
import com.example.carwash.model.entity.User;
import com.example.carwash.repository.ConfirmationTokenRepository;
import com.example.carwash.repository.UserRepository;
import com.example.carwash.service.interfaces.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {


    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final UserRepository userRepository;

    @Autowired
    public ConfirmationTokenServiceImpl(ConfirmationTokenRepository confirmationTokenRepository, UserRepository userRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.userRepository = userRepository;
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
        userRepository.save(user);
        confirmationTokenRepository.delete(confirmationTokenRepository.findByUserId(user.getId()).get());
    }
    @Override
    public void deleteToken(ConfirmationToken valid) {
        confirmationTokenRepository.delete(valid);
    }
}
