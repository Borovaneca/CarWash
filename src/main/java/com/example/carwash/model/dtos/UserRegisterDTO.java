package com.example.carwash.model.dtos;

import com.example.carwash.validation.passwordMatcher.PasswordMatcher;
import com.example.carwash.validation.uniqueEmail.UniqueEmail;
import com.example.carwash.validation.uniqueUsername.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@PasswordMatcher(
        password = "password",
        confirmPassword = "confirmPassword"
)
public class UserRegisterDTO {

    @NotBlank
    @Size(min = 3, max = 20)
    @UniqueUsername
    private String username;

    @Email
    @NotBlank
    @UniqueEmail
    private String email;

    @NotBlank
    @Size(min = 8, max = 20)
    private String password;

    @NotBlank
    @Size(min = 8, max = 20)
    private String confirmPassword;


    private MultipartFile image;

}
