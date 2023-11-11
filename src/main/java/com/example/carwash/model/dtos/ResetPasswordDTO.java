package com.example.carwash.model.dtos;

import com.example.carwash.validation.passwordMatcher.PasswordMatcher;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@PasswordMatcher(
        password = "password",
        confirmPassword = "confirmPassword"
)
@Setter
@Getter
@NoArgsConstructor
public class ResetPasswordDTO {
    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword;
}
