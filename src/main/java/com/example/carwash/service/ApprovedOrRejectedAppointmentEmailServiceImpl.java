package com.example.carwash.service;

import com.example.carwash.events.ApprovedOrRejectedAppointmentEvent;
import com.example.carwash.service.interfaces.ApprovedOrRejectedAppointmentEmailService;
import com.example.carwash.service.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ApprovedOrRejectedAppointmentEmailServiceImpl implements ApprovedOrRejectedAppointmentEmailService {

    private final EmailService emailService;

    @Autowired
    public ApprovedOrRejectedAppointmentEmailServiceImpl(EmailService emailService) {
        this.emailService = emailService;
    }


    @Override
    @EventListener(ApprovedOrRejectedAppointmentEvent.class)
    public void sendAcceptedOrRejectedAppointmentEmail(ApprovedOrRejectedAppointmentEvent event) {
        emailService.sendAcceptedOrRejectedAppointmentEmail(event.getUsername(), event.getEmail(), event.getDate(), event.getTime(), event.getStatus());
    }
}
