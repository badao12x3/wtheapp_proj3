package com.example.weatherapp.Adapter;

import static com.example.weatherapp.Constant.API;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.API.WeatherApiService;
import com.example.weatherapp.Activities.CityListActivity;
import com.example.weatherapp.CityDeleteInterface;
import com.example.weatherapp.Models.CityInfoResponse;
import com.example.weatherapp.Models.ForecastResponse;
import com.example.weatherapp.OnCityItemClickListener;
import com.example.weatherapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder>{
    private List<CityInfoResponse> city;
    private CityDeleteInterface cityDeleteInterface;
    private OnCityItemClickListener onCityItemClickListener;
    private Context mContext;
    private String units;
    SharedPreferences sharedPreferences;
    List<CityInfoResponse> arrayList;


    public CityListAdapter(List<CityInfoResponse> city, CityDeleteInterface cityDeleteInterface, OnCityItemClickListener onCityItemClickListener, String units) {
        this.city = city;
        this.cityDeleteInterface = cityDeleteInterface;
        this.onCityItemClickListener = onCityItemClickListener;
        this.units = units;

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        switch (viewType){
            case 0: {
                View view0 = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_list_0,parent,false);
                return new ViewHolder(view0, onCityItemClickListener);
            }
            default: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_list,parent,false);
                return new ViewHolder(view, onCityItemClickListener);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvCity.setText(city.get(position).getName());
        String lat = city.get(position).getLat().toString();
        String lon = city.get(position).getLon().toString();
        Call<ForecastResponse> call = WeatherApiService.weatherApiService.getCurrentWeatherData(lat,lon,"minutely",API, units, "vi");
        call.enqueue(new Callback<ForecastResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
                if(response.code() == 200){
                    ForecastResponse weatherResponse = response.body();
                    Double temp = weatherResponse.getCurrent().getTemp();
                    int tempInt =temp.intValue();
                    if(units.equals("metric"))
                        holder.tvTempList.setText(tempInt + "°C");
                    else if (units.equals("imperial"))
                        holder.tvTempList.setText(tempInt + "°F");
                    else holder.tvTempList.setText(tempInt + " K");
                    holder.tvTempInfo.setText(weatherResponse.getCurrent().getWeather().get(0).getMain());
                }

            }

            @Override
            public void onFailure(Call<ForecastResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Lỗi...!!!");
                builder.setIcon(R.drawable.ic_baseline_error_outline_24);
                builder.setMessage("Không có kết quả thành phố, gọi Api thất bại");
                builder.setCancelable(true);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create().show();
                Toast.makeText(mContext,"Không có kết quả,gọi Api thất bại", Toast.LENGTH_LONG).show();
            }
        });
        if(position != 0){
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sharedPreferences = mContext.getSharedPreferences("CityList3", AppCompatActivity.MODE_PRIVATE);
                    Gson gson = new Gson();
                    String json = sharedPreferences.getString("CityList3","");
                    Type type = new TypeToken<ArrayList<CityInfoResponse>>() {}.getType();
                    arrayList = gson.fromJson(json, type);
                    arrayList.remove(position);
                    String json1 = gson.toJson(arrayList);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("CityList3", json1).apply();
                    city.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position,city.size());
                    cityDeleteInterface.getFlag(true);
                    Toast.makeText(mContext,"Xóa thành công!", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Xóa thất bại...!!!");
                    builder.setIcon(R.drawable.ic_baseline_error_outline_24);
                    builder.setMessage("Bạn không thể xóa thành phố nơi bạn đang ở hiện tại.");
                    builder.setCancelable(true);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.create().show();
//                    Toast.makeText(mContext,"Bạn không thể xóa thành phố nơi bạn đang ở hiện tại!", Toast.LENGTH_LONG ).show();
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return city.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvCity;
        TextView tvTempList;
        TextView tvTempInfo;
        ImageButton btnDelete;
        OnCityItemClickListener _onCityItemClickListener;

        public ViewHolder(@NonNull View itemView, OnCityItemClickListener onCityItemClickListener) {
            super(itemView);
            tvCity = itemView.findViewById(R.id.tvCity);
            tvTempList = itemView.findViewById(R.id.tvTempList);
            tvTempInfo = itemView.findViewById(R.id.tvTempInfo);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            _onCityItemClickListener = onCityItemClickListener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (_onCityItemClickListener != null){
                        _onCityItemClickListener.onItemClick(getAdapterPosition());
                    }
                }
            });

        }
    }
}
