package com.example.carwash.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudinaryConfig {

    private final String CLOUD_NAME = "dy2y8i2de";
    private final String API_KEY = "111187215981859";
    private final String API_SECRET = "H3gM21c_0mYpOPklNO3pR11s8HE";

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = Map.of(
                "cloud_name", CLOUD_NAME,
                "api_key", API_KEY,
                "api_secret", API_SECRET
                );
        return new Cloudinary(config);
    }
}
