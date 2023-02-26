package com.example.weatherapp.API;

import com.example.weatherapp.Models.CityInfoResponse;
import com.example.weatherapp.Models.ForecastResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {

    String BASE_URL  = "https://api.openweathermap.org/";

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .setLenient()
            .create();

    WeatherApiService weatherApiService = new  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(WeatherApiService.class);

    @GET("data/2.5/onecall?")
    Call<ForecastResponse> getCurrentWeatherData(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("exclude") String exclude,
            @Query("appid") String appid,
            @Query("units") String units,
            @Query("lang") String lang
            );
    @GET("geo/1.0/direct?")
    Call<List<CityInfoResponse>> getCityInfo(
            @Query("q") String q,
            @Query("limit") String limit,
            @Query("appid") String appid
    );
}
