package com.example.carwash.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserRegisteredEvent extends ApplicationEvent {

    private final String username;
    private final String email;
    private final String token;

    public UserRegisteredEvent(Object source, String email, String username, String token) {
        super(source);
        this.username = username;
        this.email = email;
        this.token = token;
    }


}
