package com.example.weatherapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.Models.Daily;
import com.example.weatherapp.Models.Hourly;
import com.example.weatherapp.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

public class HourlyForecastAdapter extends RecyclerView.Adapter<HourlyForecastAdapter.ViewHolder> {
    private Context mContext;
    private List<Hourly> data;
    private Integer Timezone_offset;
    private String  now;
    private String  today;
    private String  units;


    public HourlyForecastAdapter(List<Hourly> data,Integer Timezone_offset, String  now, String  today, String  units) {
        this.data = data;
        this.Timezone_offset = Timezone_offset;
        this.now = now;
        this.today = today;
        this.units = units;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_list,parent,false);
        mContext = parent.getContext();
        return new HourlyForecastAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hourly hourly = data.get(position);


        holder.tvHour.setText(hourly.getDt().toString());

        String icon = hourly.getWeather().get(0).getIcon();
        String iconString = "http://openweathermap.org/img/w/"+icon+".png";
        Glide.with(mContext).load(iconString).into(holder.imageHourForecastWeather);

        Double temp = hourly.getTemp();
        int minTempInt = temp.intValue();

        if(units.equals("metric"))
            holder.tvTempHour.setText(minTempInt+"°C");
        else if (units.equals("imperial"))
            holder.tvTempHour.setText(minTempInt+"°F");
        else holder.tvTempHour.setText(minTempInt+" K");


        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("hh a");
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf2 = new SimpleDateFormat("hh");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String weatherHour = sdf.format((long)(hourly.getDt() + Timezone_offset - 25200)*1000);
        String weatherDate = sdf1.format((long)(hourly.getDt() + Timezone_offset - 25200)*1000);
        if(Objects.equals(now, weatherHour)&& Objects.equals(today, weatherDate)) {
            holder.tvHour.setText("Bây giờ");
        }
        else {
            holder.tvHour.setText(sdf2.format((long)(hourly.getDt() + Timezone_offset - 25200)*1000));
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder {

        private TextView tvHour;
        private TextView tvTempHour;
        private ImageView imageHourForecastWeather;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvHour = itemView.findViewById(R.id.tvHour);
            tvTempHour = itemView.findViewById(R.id.tvTempHour);
            imageHourForecastWeather = itemView.findViewById(R.id.imageHourForecastWeather);

        }
    }
}
