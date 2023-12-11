package com.example.rentcarapp;

public class CarModel {

    private String carId;
    private String modelName;
    private String description;
    private String price;
    private String images;

    // Constructeur par défaut requis par Firebase
    public CarModel() {
    }

    public CarModel(String carId, String modelName, String description, String price, String images) {
        this.carId = carId;
        this.modelName = modelName;
        this.description = description;
        this.price = price;
        this.images = images;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}

