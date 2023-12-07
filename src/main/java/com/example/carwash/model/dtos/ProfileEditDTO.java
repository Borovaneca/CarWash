package com.example.carwash.model.dtos;

import com.example.carwash.validation.validNewEmail.ValidNewEmailAndUsername;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    @Pattern(regexp = "^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email!")
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
                ", email='" + email + '\'' +
                ", age=" + age +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", city='" + city + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }
}