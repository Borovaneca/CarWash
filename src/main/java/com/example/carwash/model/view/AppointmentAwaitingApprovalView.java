package com.example.carwash.model.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AppointmentAwaitingApprovalView {

    private String id;

    private String createBy;

    private String createOn;

    private String vehicle;

    private String madeFor;

    private String service;

    private String price;

}
