package com.example.carwash.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "resset_password")
@Getter
@Setter
@NoArgsConstructor
public class ResetPassword extends BaseEntity {

    @Column
    private String token;
    @Column(unique = true)
    private String username;

    @OneToOne
    private User user;
}
