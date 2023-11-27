package com.example.carwash.service.interfaces;

import com.example.carwash.events.ForgotPasswordEvent;
import com.example.carwash.model.entity.User;
import org.springframework.context.event.EventListener;

public interface ResetService {
    @EventListener(ForgotPasswordEvent.class)
    void userResetPassword(ForgotPasswordEvent event);

    void makeTokenAndSaveIt(User user);

    void resetPasswordForUserAndDeleteToken(String username, String password);

    boolean isValid(String token, String username);
}
