package com.example.carwash.events.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ForgotPasswordEvent extends ApplicationEvent {

    private final String email;
    private final String token;
    private final String username;

    public ForgotPasswordEvent(Object source, String email, String token, String username) {
        super(source);
        this.email = email;
        this.token = token;
        this.username = username;
    }
}
