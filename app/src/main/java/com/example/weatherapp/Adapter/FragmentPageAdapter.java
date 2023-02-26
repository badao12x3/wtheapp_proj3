package com.example.weatherapp.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.weatherapp.Fragments.WeatherScreenFragment;
import com.example.weatherapp.Models.CityInfoResponse;
import com.google.gson.Gson;

import java.util.List;

public class FragmentPageAdapter extends FragmentStateAdapter {
    private final List<CityInfoResponse> arrayList;
    private final String units;

    public FragmentPageAdapter(@NonNull FragmentActivity fragmentActivity, List<CityInfoResponse> arrayList, String units) {
        super(fragmentActivity);
        this.arrayList = arrayList;
        this.units = units;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return WeatherScreenFragment.newInstance(new Gson().toJson(arrayList.get(position)), units);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}
