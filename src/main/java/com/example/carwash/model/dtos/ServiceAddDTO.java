package com.example.carwash.model.dtos;

import com.example.carwash.validation.uniqueServiceName.UniqueServiceName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceAddDTO {

    @NotBlank
    @UniqueServiceName
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    @URL
    private String urlVideo;

    @NotNull
    @Positive
    private Double price;
}
