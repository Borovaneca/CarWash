package com.example.carwash.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
public class Appointment extends BaseEntity {

    @Column(nullable = false)
    private LocalDateTime createOn;

    @Column(nullable = false)
    @Future
    private LocalDateTime madeFor;

    @ManyToOne(fetch = FetchType.EAGER)
    private Service service;

    @Column(nullable = false)
    private Integer status = 0;

    @Column
    private boolean expired;

    @ManyToOne(fetch = FetchType.EAGER)
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
}
