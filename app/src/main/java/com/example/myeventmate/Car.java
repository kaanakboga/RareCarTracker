package com.example.myeventmate;

public class Car {
    private String brand;
    private String model;
    private double latitude;
    private double longitude;

    // Firebase için boş constructor gerekiyor
    public Car() {}

    public Car(String brand, String model, double latitude, double longitude) {
        this.brand = brand;
        this.model = model;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
