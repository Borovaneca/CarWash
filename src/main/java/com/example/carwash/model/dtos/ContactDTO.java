package com.example.carwash.model.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ContactDTO {

    @NotBlank
    private String name;

    @NotBlank
    @Pattern(regexp = "^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email!")
    private String email;

    @NotBlank
    private String message;

}
