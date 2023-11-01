package com.example.carwash.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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

    @Column
    private String question;

    @Column(nullable = false)
    private Integer status = 0;

    @OneToOne
    private Vehicle vehicle;

    @ManyToOne
    private User client;
}
