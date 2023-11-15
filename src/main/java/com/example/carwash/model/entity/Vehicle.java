package com.example.carwash.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
public class Vehicle extends BaseEntity {

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String color;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "vehicle")
    private List<Appointment> appointments;

    @Override
    public String toString() {
        return "Vehicle{" +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    public String getFullCarInfo() {
        return String.format("%s - %s - %s", brand, model, color);
    }
}
