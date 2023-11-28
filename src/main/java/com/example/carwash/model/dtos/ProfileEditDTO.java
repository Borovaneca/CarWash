package com.example.carwash.model.dtos;

import com.example.carwash.validation.passwordMatcher.PasswordMatcher;
import com.example.carwash.validation.validNewEmail.ValidNewEmailAndUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
        confirmPassword = "confirmPassword"
)
@ValidNewEmailAndUsername(
        email = "email",
        id = "id",
        username = "username"
)
public class ProfileEditDTO {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @Length(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String password;

    @Length(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String confirmPassword;

    @Email
    @NotBlank(message = "Email cannot be blank")
    @Size(max = 50, message = "Email cannot be longer than 50 characters")
    private String email;

    private Integer age;

    private String firstName;

    private String lastName;


    private String city;


    private String bio;

    @Override
    public String toString() {
        return "ProfileEditDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + "[HIDDEN]" + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", city='" + city + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }
}
