package com.example.carwash.model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String bio;

    @Column
    private Integer age;

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
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private List<Role> roles;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Vehicle> vehicles;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<Appointment> appointments;

    @OneToOne(fetch = FetchType.EAGER)
    private ProfileImage image;

    @OneToMany(mappedBy = "user",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private Set<SocialMedia> socialMedias;

    public String getFullName() {
        return firstName + " " + lastName;
    }


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", bio='" + bio + '\'' +
                ", age=" + age +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", registeredOn=" + registeredOn +
                ", isActive=" + isActive +
                ", roles=" + roles +
                ", vehicles=" + vehicles +
                ", appointments=" + appointments +
                ", image=" + image +
                ", socialMedias=" + socialMedias +
                '}';
    }
}
