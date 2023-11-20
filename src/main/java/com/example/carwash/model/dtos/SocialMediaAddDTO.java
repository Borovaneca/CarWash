package com.example.carwash.model.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@NoArgsConstructor
@Setter
@Getter
public class SocialMediaAddDTO {
    @NotNull
    private String username;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Invalid social media name")
    private String type;
    @NotBlank
    @URL
    private String link;

}
