package com.example.weatherapp.Models;

import java.util.List;

public class ForecastResponse {
    private Current current;
    private List<Daily> daily;
    private List<Hourly> hourly;
    private Double lat;
    private Double lon;
    private String timezone;
    private Integer timezone_offset;

    public ForecastResponse(Current current, List<Daily> daily, Double lat, Double lon, String timezone, Integer timezone_offset, List<Hourly> hourly) {
        this.current = current;
        this.daily = daily;
        this.lat = lat;
        this.lon = lon;
        this.timezone = timezone;
        this.timezone_offset = timezone_offset;
        this.hourly = hourly;
    }

    public List<Hourly> getHourly() {
        return hourly;
    }

    public void setHourly(List<Hourly> hourly) {
        this.hourly = hourly;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public List<Daily> getDaily() {
        return daily;
    }

    public void setDaily(List<Daily> daily) {
        this.daily = daily;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Integer getTimezone_offset() {
        return timezone_offset;
    }

    public void setTimezone_offset(Integer timezone_offset) {
        this.timezone_offset = timezone_offset;
    }
}


