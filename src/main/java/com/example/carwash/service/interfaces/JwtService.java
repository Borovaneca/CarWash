package com.example.carwash.service.interfaces;


import com.example.carwash.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

     String generateToken(User user);
     String getUsernameFromToken(String token);
     boolean isTokenValid(String token, UserDetails userDetails);
}
