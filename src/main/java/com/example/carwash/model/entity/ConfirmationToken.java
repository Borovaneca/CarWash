package com.example.carwash.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "confirmation_token")
public class ConfirmationToken extends BaseEntity {

    @Column(nullable = false)
    private String token;

    @ManyToOne
    private User user;

    public ConfirmationToken(String token, User user) {
        this.token = token;
        this.user = user;
    }


}
