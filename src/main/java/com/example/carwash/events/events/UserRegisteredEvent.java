package com.example.carwash.events.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;

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
