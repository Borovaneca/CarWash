package com.example.carwash.model.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VehicleAddDTO {

    @NotBlank
    private String brand;
    @NotBlank
    private String model;
    @NotBlank
    private String color;
}
