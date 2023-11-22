package com.example.carwash.utils.jwt;


import com.example.carwash.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

     String generateToken(User user);
     String getUsernameFromToken(String token);
     boolean isTokenValid(String token, UserDetails userDetails);
}
