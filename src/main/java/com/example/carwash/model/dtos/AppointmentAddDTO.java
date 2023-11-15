package com.example.carwash.model.dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AppointmentAddDTO {

    @Future
    @NotNull
    private LocalDateTime madeFor;

    @NotNull
    private String service;

    @NotNull
    private Long vehicleId;
}
