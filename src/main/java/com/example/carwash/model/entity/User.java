package com.example.carwash.model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

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
    @JoinColumn(
            name = "role_id", referencedColumnName = "id")
    @JoinTable(name = "users_roles"
    )
    private List<Role> roles;
}
