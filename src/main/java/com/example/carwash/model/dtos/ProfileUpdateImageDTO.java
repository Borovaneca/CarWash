package com.example.carwash.model.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class ProfileUpdateImageDTO {

    @NotBlank
    private String username;

    @NotNull
    private MultipartFile image;

    public ProfileUpdateImageDTO() {
    }

    public String getUsername() {
        return username;
    }

    public ProfileUpdateImageDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public MultipartFile getImage() {
        return image;
    }

    public ProfileUpdateImageDTO setImage(MultipartFile image) {
        this.image = image;
        return this;
    }
}
