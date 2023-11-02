package com.example.carwash.model.view;

import com.example.carwash.model.entity.Appointment;
import com.example.carwash.model.entity.ProfileImage;
import com.example.carwash.model.entity.Role;
import com.example.carwash.model.entity.Vehicle;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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

    private Integer vehicles;

    private Integer appointments;

    private String locatedOn;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "ProfileView{" +
                "username='" + username + '\'' +
                ", age=" + (age == null ? "[N/A]" : age) +
                ", firstName='" + (firstName == null ? "[N/A]" : firstName) + '\'' +
                ", lastName='" + (lastName == null ? "[N/A]" : lastName) + '\'' +
                ", email='" + email + '\'' +
                ", city='" + (city == null ? "[N/A]" : city) + '\'' +
                ", registeredOn=" + registeredOn +
                ", isActive=" + isActive +
                ", role='" + role + '\'' +
                ", vehicles=" + vehicles +
                ", appointments=" + appointments +
                ", locatedOn='" + locatedOn + '\'' +
                '}';
    }
}
