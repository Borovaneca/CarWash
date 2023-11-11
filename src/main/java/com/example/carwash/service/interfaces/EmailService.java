package com.example.carwash.service.interfaces;

import com.example.carwash.model.dtos.ContactDTO;
import com.example.carwash.model.entity.User;

public interface EmailService {

    void sendRegistrationEmail(String email, String username, String token);

    void sendResetPasswordEmail(String username, String email, String token);

    void receiveComment(ContactDTO contactDTO);
}
