package com.example.carwash.model.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MyAppointmentView {

    private String createOn;

    private String madeFor;

    private String service;

    private String vehicle;

    private String status;

    private String price;
}
