package com.example.weatherapp.Models;

import java.util.List;

public class Hourly {
    private Integer clouds;//
    private Double dew_point;//
    private Integer dt;//
    private Double feels_like;//
    private Integer humidity;//
    private Double pop;//
    private Integer pressure;//
    private Rain rain;//
    private Double temp;//
    private Double uvi;//
    private Integer visibility;//
    private List<WeatherX> weather;//////
    private Integer wind_deg;//
    private Double wind_gust;//
    private Double wind_speed;//

    public Hourly(Integer clouds, Double dew_point, Integer dt, Double feels_like, Integer humidity, Double pop, Integer pressure, Rain rain, Double temp, Double uvi, Integer visibility, List<WeatherX> weather, Integer wind_deg, Double wind_gust, Double wind_speed) {
        this.clouds = clouds;
        this.dew_point = dew_point;
        this.dt = dt;
        this.feels_like = feels_like;
        this.humidity = humidity;
        this.pop = pop;
        this.pressure = pressure;
        this.rain = rain;
        this.temp = temp;
        this.uvi = uvi;
        this.visibility = visibility;
        this.weather = weather;
        this.wind_deg = wind_deg;
        this.wind_gust = wind_gust;
        this.wind_speed = wind_speed;
    }

    public Integer getClouds() {
        return clouds;
    }

    public Double getDew_point() {
        return dew_point;
    }

    public Integer getDt() {
        return dt;
    }

    public Double getFeels_like() {
        return feels_like;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public Double getPop() {
        return pop;
    }

    public Integer getPressure() {
        return pressure;
    }

    public Rain getRain() {
        return rain;
    }

    public Double getTemp() {
        return temp;
    }

    public Double getUvi() {
        return uvi;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public List<WeatherX> getWeather() {
        return weather;
    }

    public Integer getWind_deg() {
        return wind_deg;
    }

    public Double getWind_gust() {
        return wind_gust;
    }

    public Double getWind_speed() {
        return wind_speed;
    }

    public void setClouds(Integer clouds) {
        this.clouds = clouds;
    }

    public void setDew_point(Double dew_point) {
        this.dew_point = dew_point;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public void setFeels_like(Double feels_like) {
        this.feels_like = feels_like;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public void setPop(Double pop) {
        this.pop = pop;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public void setUvi(Double uvi) {
        this.uvi = uvi;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public void setWeather(List<WeatherX> weather) {
        this.weather = weather;
    }

    public void setWind_deg(Integer wind_deg) {
        this.wind_deg = wind_deg;
    }

    public void setWind_gust(Double wind_gust) {
        this.wind_gust = wind_gust;
    }

    public void setWind_speed(Double wind_speed) {
        this.wind_speed = wind_speed;
    }
}
