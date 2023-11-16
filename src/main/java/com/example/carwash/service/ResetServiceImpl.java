package com.example.carwash.service;

import com.example.carwash.model.entity.ResetPassword;
import com.example.carwash.model.entity.User;
import com.example.carwash.events.events.ForgotPasswordEvent;
import com.example.carwash.repository.ResetPasswordRepository;
import com.example.carwash.repository.UserRepository;
import com.example.carwash.service.interfaces.EmailService;
import com.example.carwash.service.interfaces.ResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ResetServiceImpl implements ResetService {


    private final EmailService emailService;
    private final ResetPasswordRepository resetPasswordRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ResetServiceImpl(EmailService emailService, ResetPasswordRepository resetPasswordRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.emailService = emailService;
        this.resetPasswordRepository = resetPasswordRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    @EventListener(ForgotPasswordEvent.class)
    public void userResetPassword(ForgotPasswordEvent event) {
        emailService.sendResetPasswordEmail(event.getUsername(), event.getEmail(),  event.getToken());
    }

    @Override
    public ResetPassword makeTokenAndSaveIt(User user) {
        ResetPassword resetPassword = new ResetPassword();
        resetPassword.setToken(UUID.randomUUID().toString());
        resetPassword.setUser(user);
        resetPassword.setUsername(user.getUsername());
       return resetPasswordRepository.save(resetPassword);
    }

    @Override
    public void resetPasswordForUserAndDeleteToken(String username, String password) {
        userRepository.findByUsername(username).ifPresent(us -> {
            us.setPassword(passwordEncoder.encode(password));
            userRepository.save(us);
            resetPasswordRepository.findByUsername(username).ifPresent(resetPasswordRepository::delete);
        });

    }

    @Override
    public boolean isValid(String token, String username) {
        Optional<ResetPassword> tokenFromDB = resetPasswordRepository.findByTokenAndUsername(token, username);
        return tokenFromDB.map(resetPassword -> resetPassword.getUsername().equals(username) && resetPassword.getToken().equals(token)).orElse(false);
    }
}
