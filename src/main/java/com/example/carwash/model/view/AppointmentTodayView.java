package com.example.carwash.model.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AppointmentTodayView {

    private String createOn;

    private String appointmentHour;

    private String madeBy;

    private String service;

    private String vehicle;

    private String price;
}
