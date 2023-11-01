package com.example.carwash.model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private Integer age;

    @Column
    private String imageUrl;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String city;

    @Column
    private LocalDate registeredOn;

    @Column
    private boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    private List<Role> roles;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Vehicle> vehicles;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<Appointment> appointments;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
