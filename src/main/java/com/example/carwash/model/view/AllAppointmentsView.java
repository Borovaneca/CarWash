package com.example.carwash.model.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllAppointmentsView {

    private Long id;
    private String createdBy;
    private String madeOn;
    private String madeFor;
    private String service;
    private String vehicle;
    private String price;
    private String status;
}
