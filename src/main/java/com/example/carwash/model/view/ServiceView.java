package com.example.carwash.model.view;

public class ServiceView {

    private Long id;
    private String name;
    private String description;
    private String price;
    private String urlVideo;

    public ServiceView() {
    }

    public Long getId() {
        return id;
    }

    public ServiceView setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ServiceView setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ServiceView setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public ServiceView setPrice(String price) {
        this.price = price;
        return this;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public ServiceView setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
        return this;
    }
}
