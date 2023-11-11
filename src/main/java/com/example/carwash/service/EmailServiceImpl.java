package com.example.carwash.service;

import com.example.carwash.model.dtos.ContactDTO;
import com.example.carwash.model.entity.User;
import com.example.carwash.service.interfaces.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.UUID;

import static com.example.carwash.constants.EmailSubjects.*;

@Service
public class EmailServiceImpl implements EmailService {

    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;
    private final String carWash;

    @Autowired
    public EmailServiceImpl(TemplateEngine templateEngine,
                            JavaMailSender javaMailSender,
                            @Value("${spring.mail.carwash}") String carWash) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
        this.carWash = carWash;
    }

    @Override
    public void sendRegistrationEmail(String email, String username, String token) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setFrom(carWash);
            mimeMessageHelper.setReplyTo(carWash);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject(REGISTRATION_SUBJECT);
            mimeMessageHelper.setText(generateRegistrationEmailBodyForRegistration(username, token), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendResetPasswordEmail(String username, String email, String token) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setFrom(carWash);
            mimeMessageHelper.setReplyTo(carWash);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject(RESET_PASSWORD_SUBJECT);
            mimeMessageHelper.setText(generateRegistrationEmailBodyForForgottenPassword(username, token), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void receiveComment(ContactDTO contactDTO) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setFrom(carWash);
            mimeMessageHelper.setReplyTo(carWash);
            mimeMessageHelper.setTo(carWash);
            mimeMessageHelper.setSubject(String.format(CONTACT, contactDTO.getName()));
            mimeMessageHelper.setText(generateContactBody(contactDTO.getName(), contactDTO.getEmail(), contactDTO.getMessage()), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateContactBody(String name, String email, String message) {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("email", email);
        context.setVariable("message", message);
        return templateEngine.process("email/contact", context);
    }

    private String generateRegistrationEmailBodyForForgottenPassword(String username, String token) {
        Context context = new Context();
        context.setVariable("token", token);
        context.setVariable("username", username);
        return templateEngine.process("email/forgot-password", context);
    }


    private String generateRegistrationEmailBodyForRegistration(String username, String token) {
        Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("token", token);
        return templateEngine.process("email/registration-email", context);
    }
}
