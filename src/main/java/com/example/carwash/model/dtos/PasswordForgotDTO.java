package com.example.carwash.model.dtos;

import jakarta.validation.constraints.NotBlank;
public record PasswordForgotDTO(@NotBlank String email) {

}
