package com.example.carwash.model.view;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
public class ProfileView {

    private String username;

    private Integer age;

    private String firstName;

    private String lastName;

    private String email;

    private String city;

    private LocalDate registeredOn;

    private boolean isActive;

    private String role;

    private String vehicles;

    private String appointments;

    private String locatedOn;

    private String bio;

    private Set<SocialMediaView> socials;

    public String getFullName() {
        return firstName + " " + lastName;
    }


    @Override
    public String toString() {
        return "ProfileView{" +
                "username='" + username + '\'' +
                ", age=" + age +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", registeredOn=" + registeredOn +
                ", isActive=" + isActive +
                ", role='" + role + '\'' +
                ", vehicles=" + vehicles +
                ", appointments=" + appointments +
                ", locatedOn='" + locatedOn + '\'' +
                ", bio='" + bio + '\'' +
                ", socials=" + socials +
                '}';
    }
}
