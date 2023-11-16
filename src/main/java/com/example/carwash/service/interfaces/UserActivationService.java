package com.example.carwash.service.interfaces;

import com.example.carwash.events.events.UserRegisteredEvent;

public interface UserActivationService {

    void userRegistered(UserRegisteredEvent event);
}
