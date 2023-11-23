package com.example.carwash.model.entity;

import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

import java.util.List;

@Entity
@Table(name = "services")
public class Service extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(unique = true, nullable = false)
    private String urlVideo;

    @Column(nullable = false)
    @Positive
    private Double price;

    @OneToMany(mappedBy = "service", cascade = CascadeType.REMOVE)
    private List<Appointment> appointments;

    public Service() {
    }

    public Service(String name) {
        this.name = name;
        setAllRest();
    }

    private void setAllRest() {
        switch (this.name) {
            case "The Works Wash":
                this.urlVideo = "eWUxqVFBq74";
                this.description = "Our signature wash includes a presoak, tri-color foam bath, high-pressure rinse, hand wax and interior vacuum and windows cleaned. Your car will look showroom new!";
                this.price = 20.0;
                break;
            case "Express Wash":
                this.urlVideo = "UJ54Kxk5LsA";
                this.description = "For a quick but thorough cleaning, our express wash hits all the key areas - exterior wash and rinse, towel dry and vacuum. Great for the busy driver on the go.";
                this.price = 15.0;
                break;
            case "Interior Detailing":
                this.urlVideo = "pVWPOBmOKuM";
                this.description = "Get the inside of your vehicle detailed with a thorough vacuum, windows cleaned, mats shampooed and a wipe down and polish of all interior surfaces.";
                this.price = 40.0;
                break;
        }
    }

    public String getName() {
        return name;
    }

    public Service setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Service setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public Service setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Service setPrice(Double price) {
        this.price = price;
        return this;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public Service setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
        return this;
    }
}
