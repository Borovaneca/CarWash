package com.example.carwash.model.entity;

import com.example.carwash.model.enums.RoleName;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@Setter
@Getter
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private RoleName name;
}
