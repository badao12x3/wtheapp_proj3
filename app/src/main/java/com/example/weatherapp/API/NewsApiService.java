package com.example.weatherapp.API;

import android.content.Context;

import com.example.weatherapp.Models.ForecastResponse;
import com.example.weatherapp.Models.News;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiService {

    @GET("api/articles")
    Call<List<News>> getNewsData();
}
