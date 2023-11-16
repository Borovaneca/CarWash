package com.example.carwash.model.dtos;

import com.example.carwash.validation.validImage.ValidImage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class ProfileUpdateImageDTO {

    @NotBlank
    private String username;

    @NotNull
    @ValidImage
    private MultipartFile image;
}
