package com.example.weatherapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RadioGroup;

import com.example.weatherapp.Models.CityInfoResponse;
import com.example.weatherapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    RadioGroup unitRadioGroup;
    String units ;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        unitRadioGroup = findViewById(R.id.unit_radio_group);
        sharedPreferences = getSharedPreferences("Units",MODE_PRIVATE);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.action_settings);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.action_news:
                        startActivity(new Intent(SettingsActivity.this, NewsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.action_settings:
//                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        return true;
                }
                return false;
            }
        });

        units = sharedPreferences.getString("units","metric");
//        String unit = getIntent().getStringExtra("unit");
        if(Objects.equals(units, "metric")){
            unitRadioGroup.check(R.id.metric);
        }else if(Objects.equals(units, "imperial")){
            unitRadioGroup.check(R.id.imperial);
        }else unitRadioGroup.check(R.id.standard);

        unitRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.metric:
                        updatePreference("metric");
//                        units = "metric";
                        break;
                    case R.id.imperial:
                        updatePreference("imperial");
//                        units = "imperial";
                        break;
                    case R.id.standard:
                        updatePreference("standard");
//                        units = "standard";
                        break;
                }
            }
        });
        switch (unitRadioGroup.getCheckedRadioButtonId()){
            case R.id.metric:
                updatePreference("metric");
//                units = "metric";
                break;
            case R.id.imperial:
                updatePreference("imperial");
//                units = "imperial";
                break;
            case R.id.standard:
                updatePreference("standard");
//                units = "standard";
                break;
        };


    }

    private void updatePreference(String units) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("units",units).apply();
    }
}