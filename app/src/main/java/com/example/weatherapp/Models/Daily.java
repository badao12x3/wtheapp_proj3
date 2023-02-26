package com.example.weatherapp.Models;

import java.util.List;

public class Daily {
    private Integer clouds;
    private Double dew_point;
    private Integer dt;
    private FeelsLike feels_like;
    private Integer humidity;
    private Double moon_phase;
    private Integer moonrise;
    private Integer moonset;
    private Double pop;
    private Integer pressure;
    private Double rain;
    private Integer sunrise;
    private Integer sunset;
    private Temp temp;
    private Double uvi;
    private List<WeatherX> weather;
    private Integer wind_deg;
    private Double wind_gust;
    private Double wind_speed;

    public Daily(Integer clouds, Double dew_point, Integer dt, FeelsLike feels_like, Integer humidity, Double moon_phase, Integer moonrise, Integer moonset, Double pop, Integer pressure, Double rain, Integer sunrise, Integer sunset, Temp temp, Double uvi, List<WeatherX> weather, Integer wind_deg, Double wind_gust, Double wind_speed) {
        this.clouds = clouds;
        this.dew_point = dew_point;
        this.dt = dt;
        this.feels_like = feels_like;
        this.humidity = humidity;
        this.moon_phase = moon_phase;
        this.moonrise = moonrise;
        this.moonset = moonset;
        this.pop = pop;
        this.pressure = pressure;
        this.rain = rain;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.temp = temp;
        this.uvi = uvi;
        this.weather = weather;
        this.wind_deg = wind_deg;
        this.wind_gust = wind_gust;
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

    public FeelsLike getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(FeelsLike feels_like) {
        this.feels_like = feels_like;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getMoon_phase() {
        return moon_phase;
    }

    public void setMoon_phase(Double moon_phase) {
        this.moon_phase = moon_phase;
    }

    public Integer getMoonrise() {
        return moonrise;
    }

    public void setMoonrise(Integer moonrise) {
        this.moonrise = moonrise;
    }

    public Integer getMoonset() {
        return moonset;
    }

    public void setMoonset(Integer moonset) {
        this.moonset = moonset;
    }

    public Double getPop() {
        return pop;
    }

    public void setPop(Double pop) {
        this.pop = pop;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Double getRain() {
        return rain;
    }

    public void setRain(Double rain) {
        this.rain = rain;
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

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public Double getUvi() {
        return uvi;
    }

    public void setUvi(Double uvi) {
        this.uvi = uvi;
    }

    public List<WeatherX> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherX> weather) {
        this.weather = weather;
    }

    public Integer getWind_deg() {
        return wind_deg;
    }

    public void setWind_deg(Integer wind_deg) {
        this.wind_deg = wind_deg;
    }

    public Double getWind_gust() {
        return wind_gust;
    }

    public void setWind_gust(Double wind_gust) {
        this.wind_gust = wind_gust;
    }

    public Double getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(Double wind_speed) {
        this.wind_speed = wind_speed;
    }
}
