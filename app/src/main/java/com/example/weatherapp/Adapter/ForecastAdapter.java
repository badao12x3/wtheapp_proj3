package com.example.weatherapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.Models.Daily;
import com.example.weatherapp.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private Context mContext;
    private List<Daily> data;
    private Integer Timezone_offset;
    private String  today;
    private String units;

    public ForecastAdapter(List<Daily> data, Integer Timezone_offset, String  today, String units) {
        this.data = data;
        this.Timezone_offset = Timezone_offset;
        this.today = today;
        this.units = units;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_item_list,parent,false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Daily daily = data.get(position);


        holder.tvWeather.setText(daily.getWeather().get(0).getMain());

        String icon = daily.getWeather().get(0).getIcon();
        String iconString = "http://openweathermap.org/img/w/"+icon+".png";
        Glide.with(mContext).load(iconString).into(holder.imageForecastWeather);
//        URL url = null;
//        try {
//            url = new URL(iconString);
//            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            holder.imageForecastWeather.setImageBitmap(bmp);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//        Integer minTemp = Integer.parseInt(daily.getTemp().getMin().toString());
//        Integer maxTemp = Integer.parseInt(daily.getTemp().getMax().toString());

        Double minTemp = daily.getTemp().getMin();
        int minTempInt = minTemp.intValue();


        Double maxTemp = daily.getTemp().getMax();
        int maxTempInt = maxTemp.intValue();


        if(units.equals("metric")){
            holder.tvMinTemp.setText(minTempInt+"°C");
            holder.tvMaxTemp.setText(maxTempInt+"°C");
        }else if (units.equals("imperial")) {
            holder.tvMinTemp.setText(minTempInt+"°F");
            holder.tvMaxTemp.setText(maxTempInt+"°F");
        }
        else {
            holder.tvMinTemp.setText(minTempInt+" K");
            holder.tvMaxTemp.setText(maxTempInt+" K");
        }
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");


        String weatherDate = sdf1.format((long)(daily.getDt() + Timezone_offset - 25200)*1000);

        if(Objects.equals(today, weatherDate)) {
            holder.tvDay.setText("Hôm nay");
        }
        else {
            holder.tvDay.setText(sdf.format((long)(daily.getDt() + Timezone_offset - 25200)*1000));
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder {

        private TextView tvWeather;
        private TextView tvMinTemp;
        private TextView tvMaxTemp;
        private TextView tvDay;
        private ImageView imageForecastWeather;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvWeather = itemView.findViewById(R.id.tvWeather);
            tvMinTemp = itemView.findViewById(R.id.tvMinTemp);
            tvMaxTemp = itemView.findViewById(R.id.tvMaxTemp);
            tvDay = itemView.findViewById(R.id.tvDay);
            imageForecastWeather = itemView.findViewById(R.id.imageForecastWeather);

        }
    }
}
