package com.example.carwash.model.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class VehicleView {

    private Long id;
    private String brand;
    private String model;
    private String color;
}
