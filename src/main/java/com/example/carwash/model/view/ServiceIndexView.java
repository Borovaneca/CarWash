package com.example.carwash.model.view;

public class ServiceIndexView {

    private String name;
    private String description;

    public ServiceIndexView() {
    }

    public String getName() {
        return name;
    }

    public ServiceIndexView setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ServiceIndexView setDescription(String description) {
        this.description = description;
        return this;
    }
}
