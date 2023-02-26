package com.example.weatherapp.Models;

public class News {
    private String url;//
    private String coverImg;//
    private String describe;//
    private String publishAt;//

    public News(String url, String coverImg, String describe, String publishAt) {
        this.url = url;
        this.coverImg = coverImg;
        this.describe = describe;
        this.publishAt = publishAt;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public void setPublishAt(String publishAt) {
        this.publishAt = publishAt;
    }

    public String getUrl() {
        return url;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public String getDescribe() {
        return describe;
    }

    public String getPublishAt() {
        return publishAt;
    }
}
