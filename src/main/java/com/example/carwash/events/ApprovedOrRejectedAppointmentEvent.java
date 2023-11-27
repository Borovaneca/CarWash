package com.example.carwash.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class ApprovedOrRejectedAppointmentEvent extends ApplicationEvent {

    private final String email;
    private final String status;
    private final LocalDate date;
    private final LocalTime time;
    private final String username;

    public ApprovedOrRejectedAppointmentEvent(Object source, String username, String email, LocalDate date, LocalTime time, String status) {
        super(source);
        this.email = email;
        this.status = status;
        this.date = date;
        this.time = time;
        this.username = username;
    }
}
