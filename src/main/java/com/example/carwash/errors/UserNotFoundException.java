package com.example.carwash.errors;

import com.example.carwash.constants.ExceptionMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ExceptionMessages.USER_NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    private String username;
    public UserNotFoundException(String message, String username) {
        super(message);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
