package com.example.carwash.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudinaryConfig {


    private final String CLOUD_NAME;
    private final String API_KEY;
    private final String API_SECRET;

    public CloudinaryConfig(@Value("${cloudinary.name}") String cloudName,
                            @Value("${cloudinary.api-key}") String apiKey,
                            @Value("${cloudinary.api-secret}") String apiSecret) {
        CLOUD_NAME = cloudName;
        API_KEY = apiKey;
        API_SECRET = apiSecret;
    }

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
