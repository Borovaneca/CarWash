package com.example.carwash.model.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class SocialMediaAddDTO {
    @NotNull
    private String username;
    @NotBlank
    private String type;
    @NotBlank
    private String link;

}
