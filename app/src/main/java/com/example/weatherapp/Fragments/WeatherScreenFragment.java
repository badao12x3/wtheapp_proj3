package com.example.weatherapp.Fragments;

import static com.example.weatherapp.Constant.API;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.weatherapp.API.WeatherApiService;
import com.example.weatherapp.Activities.MainActivity;
import com.example.weatherapp.Adapter.ForecastAdapter;
import com.example.weatherapp.Adapter.HourlyForecastAdapter;
import com.example.weatherapp.Constant;
import com.example.weatherapp.Models.CityInfoResponse;
import com.example.weatherapp.Models.Daily;
import com.example.weatherapp.Models.ForecastResponse;
import com.example.weatherapp.Models.Hourly;
import com.example.weatherapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherScreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class WeatherScreenFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CITY = "city";
    private static final String ARG_UNIT = "units";

    // TODO: Rename and change types of parameters
    private String city;
    private String units;

    TextView tvCityName;
    TextView tvWeatherInfo;
    TextView tvTemp;
    TextView tvWind;
    TextView tvHumidity;
    TextView tvPressure;
    ImageView ivWeather;
    ImageView imageViewSunrise;
    ImageView imageViewWind;
    ImageView imageViewSunset;
    TextView tvSunrise;
    TextView tvSunset;
    RecyclerView recyclerViewForecast, recyclerViewHourForecast;


    public static WeatherScreenFragment newInstance(String city, String units) {
        WeatherScreenFragment fragment = new WeatherScreenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CITY, city);
        args.putString(ARG_UNIT, units);
        fragment.setArguments(args);
        return fragment;

    }

    public WeatherScreenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            city = getArguments().getString(ARG_CITY);
            units = getArguments().getString(ARG_UNIT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment\
        View view = inflater.inflate(R.layout.fragment_weather_screen2, container, false);
        tvCityName = view.findViewById(R.id.tvCityName);
        tvWeatherInfo = view.findViewById(R.id.tvWeatherInfo);
        tvTemp = view.findViewById(R.id.tvTemp);
        tvWind = view.findViewById(R.id.tvWind);
        tvHumidity = view.findViewById(R.id.tvHumidity);
        tvPressure = view.findViewById(R.id.tvPressure);
        ivWeather = view.findViewById(R.id.ivWeather);
        imageViewSunrise = view.findViewById(R.id.imageViewSunrise);
        imageViewWind = view.findViewById(R.id.imageViewWind);
        imageViewSunset = view.findViewById(R.id.imageViewSunset);
        tvSunrise = view.findViewById(R.id.tvSunrise);
        tvSunset = view.findViewById(R.id.tvSunset);
        recyclerViewForecast = view.findViewById(R.id.recyclerViewForecast);
        recyclerViewHourForecast = view.findViewById(R.id.recyclerViewHourForecast);
        getCurrentWeatherData();

        return view;
    }

    private void getCurrentWeatherData() {
        Gson gson = new Gson();
        CityInfoResponse data = gson.fromJson(city,CityInfoResponse.class);
        String lat = data.getLat().toString();
        String lon = data.getLon().toString();

        Call<ForecastResponse> call = WeatherApiService.weatherApiService.getCurrentWeatherData(lat,lon,"minutely", API, units, "vi");
        call.enqueue(new Callback<ForecastResponse>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
                if(response.code() == 200){
                    ForecastResponse weatherResponse = response.body();
                    tvCityName.setText(data.getName());

                    tvWeatherInfo.setText(weatherResponse.getCurrent().getWeather().get(0).getMain());

                    Double temp = weatherResponse.getCurrent().getTemp();
                    int tempInt = temp.intValue();

                    if(units.equals("metric")){
                        tvTemp.setText(tempInt + "°C");
                        tvWind.setText(weatherResponse.getCurrent().getWind_speed() + "m/s");
                    } else if (units.equals("imperial")){
                        tvTemp.setText(tempInt + "°F");
                        tvWind.setText(weatherResponse.getCurrent().getWind_speed() + "mph");
                    }else {
                        tvTemp.setText(tempInt + " K");
                        tvWind.setText(weatherResponse.getCurrent().getWind_speed() + "m/s");
                    }


                    tvHumidity.setText(weatherResponse.getCurrent().getHumidity() + "%");
                    tvPressure.setText(weatherResponse.getCurrent().getPressure() +"hPa");

                    String icon = weatherResponse.getCurrent().getWeather().get(0).getIcon();
                    String iconString = "http://openweathermap.org/img/w/"+icon+".png";
                    Glide.with(ivWeather.getContext()).load(iconString).into(ivWeather);

                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf2 = new SimpleDateFormat("hh a");
                    String sunrise = sdf.format((long)(weatherResponse.getCurrent().getSunrise() + weatherResponse.getTimezone_offset() - 25200)*1000);
                    String sunset = sdf.format((long)(weatherResponse.getCurrent().getSunset() + weatherResponse.getTimezone_offset() - 25200)*1000);
                    tvSunrise.setText(sunrise);
                    tvSunset.setText(sunset);

                    String today = sdf1.format((long)(weatherResponse.getCurrent().getDt() + weatherResponse.getTimezone_offset() - 25200)*1000);
                    String now = sdf2.format((long)(weatherResponse.getCurrent().getDt() + weatherResponse.getTimezone_offset() - 25200)*1000);

                    //dự báo 7 ngày
                    List<Daily> forecastData = new ArrayList<>();
                    for(Daily daily: weatherResponse.getDaily()){
                        forecastData.add(daily);
                    }
                    Integer timezone_offset = weatherResponse.getTimezone_offset();

                    ForecastAdapter forecastAdapter = new ForecastAdapter(forecastData,timezone_offset, today, units);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);

                    recyclerViewForecast.setLayoutManager(layoutManager);
                    recyclerViewForecast.setAdapter(forecastAdapter);
                    //dự báo 48h
                    List<Hourly> hourlies = new ArrayList<>();
                    for(Hourly hourly: weatherResponse.getHourly()){
                        hourlies.add(hourly);
                    }
                    HourlyForecastAdapter hourlyForecastAdapter = new HourlyForecastAdapter(hourlies, timezone_offset, now,today, units);
                    RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
                    recyclerViewHourForecast.setLayoutManager(layoutManager1);
                    recyclerViewHourForecast.setAdapter(hourlyForecastAdapter);

                }
            }

            @Override
            public void onFailure(Call<ForecastResponse> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Call Api error"+ t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }


}