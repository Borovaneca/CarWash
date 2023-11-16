package com.example.carwash.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "profile_images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileImage extends BaseEntity {

    @Column
    private String locatedOn;
}
