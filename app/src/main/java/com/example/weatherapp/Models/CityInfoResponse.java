package com.example.weatherapp.Models;

public class CityInfoResponse {
    private String country;
    private Double lat;
    private Double lon;
    private String name;

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public String getName() {
        return name;
    }

    public CityInfoResponse(String country, Double lat, Double lon, String name) {
        this.country = country;
        this.lat = lat;
        this.lon = lon;
        this.name = name;
    }
}
