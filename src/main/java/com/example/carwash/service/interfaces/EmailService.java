package com.example.carwash.service.interfaces;

import com.example.carwash.model.dtos.ContactDTO;

import java.time.LocalDate;
import java.time.LocalTime;

public interface EmailService {

    void sendRegistrationEmail(String email, String username, String token);

    void sendResetPasswordEmail(String username, String email, String token);

    void receiveComment(ContactDTO contactDTO);

    void sendAcceptedOrRejectedAppointmentEmail(String username, String email, LocalDate date, LocalTime time, String status);
}
