package com.example.carwash.model.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AppointmentVehicleDTO {

    private Long id;
    private String brand;
    private String model;
    private String color;
}
