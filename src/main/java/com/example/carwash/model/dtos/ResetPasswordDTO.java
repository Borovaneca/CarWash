package com.example.carwash.model.dtos;

import com.example.carwash.validation.passwordMatcher.PasswordMatcher;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @Size(min = 8, max = 20)
    private String password;
    @NotBlank
    @Size(min = 8, max = 20)
    private String confirmPassword;
}
