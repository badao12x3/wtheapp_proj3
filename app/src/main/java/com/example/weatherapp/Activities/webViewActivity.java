package com.example.weatherapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.example.weatherapp.API.NewsApiService;
import com.example.weatherapp.Models.News;
import com.example.weatherapp.R;

import retrofit2.Call;

public class webViewActivity extends AppCompatActivity {

    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);



        mWebView = findViewById(R.id.web_view);
        String url = getIntent().getStringExtra("url");

        if (url != null) {
            mWebView.loadUrl(url);
        }
    }
}