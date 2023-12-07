package com.example.carwash.model.dtos;
import com.example.carwash.validation.passwordMatcher.PasswordMatcher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatcher(
        password = "password",
        confirmPassword = "confirmPassword",
        message = "Passwords do not match"
)
public class UpdatePasswordDTO {

    @Length(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String password;

    @Length(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String confirmPassword;

}