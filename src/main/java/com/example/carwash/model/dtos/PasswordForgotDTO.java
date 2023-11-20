package com.example.carwash.model.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
public record PasswordForgotDTO(@NotBlank @Email String email) {

}
