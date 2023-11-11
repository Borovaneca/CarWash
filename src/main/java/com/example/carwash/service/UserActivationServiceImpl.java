package com.example.carwash.service;

import com.example.carwash.model.events.ForgotPasswordEvent;
import com.example.carwash.model.events.UserRegisteredEvent;
import com.example.carwash.service.interfaces.EmailService;
import com.example.carwash.service.interfaces.UserActivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class UserActivationServiceImpl implements UserActivationService {

    private final EmailService emailService;

    @Autowired
    public UserActivationServiceImpl(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener(UserRegisteredEvent.class)
    @Override
    public void userRegistered(UserRegisteredEvent event) {
        emailService.sendRegistrationEmail(event.getEmail(), event.getUsername(), event.getToken());
    }

}
