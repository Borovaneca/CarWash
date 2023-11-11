package com.example.carwash.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "social_media")
@Setter
@Getter
@NoArgsConstructor
public class SocialMedia extends BaseEntity {

    @Column
    private String type;

    @Column
    private String link;

    @ManyToOne()
    private User user;
}
