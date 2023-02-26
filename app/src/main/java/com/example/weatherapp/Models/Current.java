package com.example.weatherapp.Models;

import java.util.List;

public class Current {
    private Integer clouds;
    private Double dew_point;
    private Integer dt;
    private Double feels_like;
    private Integer humidity;
    private Integer pressure;
    private Integer sunrise;
    private Integer sunset;
    private Double temp;
    private Double uvi;
    private Integer visibility;
    private List<Weather> weather;
    private Integer wind_deg;
    private Double wind_speed;

    public Current(Integer clouds, Double dew_point, Integer dt, Double feels_like, Integer humidity, Integer pressure, Integer sunrise, Integer sunset, Double temp, Double uvi, Integer visibility, List<Weather> weather, Integer wind_deg, Double wind_speed) {
        this.clouds = clouds;
        this.dew_point = dew_point;
        this.dt = dt;
        this.feels_like = feels_like;
        this.humidity = humidity;
        this.pressure = pressure;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.temp = temp;
        this.uvi = uvi;
        this.visibility = visibility;
        this.weather = weather;
        this.wind_deg = wind_deg;
        this.wind_speed = wind_speed;
    }

    public Integer getClouds() {
        return clouds;
    }

    public void setClouds(Integer clouds) {
        this.clouds = clouds;
    }

    public Double getDew_point() {
        return dew_point;
    }

    public void setDew_point(Double dew_point) {
        this.dew_point = dew_point;
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public Double getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(Double feels_like) {
        this.feels_like = feels_like;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getSunrise() {
        return sunrise;
    }

    public void setSunrise(Integer sunrise) {
        this.sunrise = sunrise;
    }

    public Integer getSunset() {
        return sunset;
    }

    public void setSunset(Integer sunset) {
        this.sunset = sunset;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getUvi() {
        return uvi;
    }

    public void setUvi(Double uvi) {
        this.uvi = uvi;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Integer getWind_deg() {
        return wind_deg;
    }

    public void setWind_deg(Integer wind_deg) {
        this.wind_deg = wind_deg;
    }

    public Double getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(Double wind_speed) {
        this.wind_speed = wind_speed;
    }
}
