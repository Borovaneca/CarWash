package com.example.carwash.service.interfaces;

import com.example.carwash.events.ApprovedOrRejectedAppointmentEvent;
import org.springframework.context.event.EventListener;

public interface ApprovedOrRejectedAppointmentEmailService {


void sendAcceptedOrRejectedAppointmentEmail(ApprovedOrRejectedAppointmentEvent event);
}
