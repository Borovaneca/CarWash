package com.example.carwash.service.interfaces;

import com.example.carwash.model.entity.ConfirmationToken;
import com.example.carwash.model.entity.User;

public interface ConfirmationTokenService {

    void saveConfirmationToken(ConfirmationToken token);

    ConfirmationToken findByToken(String token);

    void confirmEmail(User user);

    void deleteToken(ConfirmationToken valid);
}
