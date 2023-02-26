package com.example.weatherapp.Activities;

import static com.example.weatherapp.Constant.API;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.API.WeatherApiService;
import com.example.weatherapp.Adapter.CityListAdapter;
import com.example.weatherapp.CityDeleteInterface;
import com.example.weatherapp.Models.CityInfoResponse;
import com.example.weatherapp.OnCityItemClickListener;
import com.example.weatherapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityListActivity extends AppCompatActivity implements CityDeleteInterface, OnCityItemClickListener {

    private static final int REQUEST_CODE_ADD_CITY_BY_MAP = 100;
    SharedPreferences sharedPreferences;
    List<CityInfoResponse> arrayList;
    Boolean flag = false;
    Boolean flagDelete = false;

    ImageButton btnSearch;
    ImageButton btnMap;
    AutoCompleteTextView edtSearch;
    RecyclerView recyclerViewCityList;
    String units = "metric";
    String[] CITIES = {
        "New York",
                "Paris",
                "London",
                "Tokyo",
                "Los Angeles",
                "Beijing",
                "Rome",
                "Miami",
                "Sydney",
                "San Francisco",
                "Toronto",
                "Madrid",
                "Moscow",
                "Berlin",
                "Hong Kong",
                "Barcelona",
                "Chicago",
                "Dubai",
                "Vienna",
                "Singapore",
                "Amsterdam",
                "Shanghai",
                "Seoul",
                "Munich",
                "San Diego",
                "Boston",
                "Dublin",
                "Seattle",
                "Milan",
                "Brussels",
                "Zurich",
                "Copenhagen",
                "Athens",
                "Prague",
                "Melbourne",
                "Oslo",
                "Vancouver",
                "Hamburg",
                "Stockholm",
                "Kuala Lumpur",
                "Marseille",
                "Warsaw",
                "Lisbon",
                "Budapest",
                "Buenos Aires",
                "Krakow",
                "Cairo",
                "Dubrovnik",
                "Helsinki",
                "Nice",
                "Lyon",
                "Bruges",
                "Santorini",
                "Jerusalem",
                "Salzburg",
                "Valencia",
                "Riga",
                "Granada",
                "Tallinn",
                "Innsbruck",
                "Naples",
                "Split",
                "Bologna",
                "Verona",
                "Florence",
                "Siena",
                "Pisa",
                "Dubrovnik",
                "Porto",
                "Seville",
                "Cordoba",
                "Istanbul",
                "Gdansk",
                "Lviv",
                "Rijeka",
                "Krakow",
                "Nuremberg",
                "Ljubljana",
                "Zagreb",
                "Sofia",
                "Cluj-Napoca",
                "Bucharest",
                "Belgrade",
                "Skopje",
                "Bansko",
                "Ohrid",
                "Tbilisi",
                "Yerevan",
                "Vilnius",
                "Bratislava",
                "Kosice",
                "Prishtina",
                "Kotor",
                "Bled",
                "Sarajevo",
                "Mostar",
                "Bergen",
                "St. Petersburg",
                "Cape Town",
                "Johannesburg",
                "Casablanca",
                "Cancun",
                "Montreal",
                "Quebec City",
                "Vancouver",
                "Calgary",
                "Edmonton",
                "Halifax",
                "Victoria",
                "Saskatoon",
                "Winnipeg",
                "Ottawa",
                "Toronto",
                "Niagara Falls",
                "Banff",
                "Whistler",
                "Yellowknife",
                "Iqaluit",
                "Whitehorse","Hà Nội", "Hồ Chí Minh", "Đà Nẵng", "Hải Phòng", "Cần Thơ", "Hải Dương", "Huế", "Hà Tĩnh", "Hà Giang", "Hà Nam", "Hà Tây", "Hà Giang", "Hà Nam", "Hà Tây", "Hà Đông", "Hà Nam Ninh", "Hà Tiên", "Hạ Long", "Hậu Lộc", "Hòa Bình", "Hội An", "Kiên Giang", "Kon Tum", "Lạng Sơn", "Lào Cai", "Long Xuyên", "Móng Cái", "Mỹ Tho", "Nam Định", "Nha Trang", "Ninh Bình", "Ninh Thuận", "Phan Rang", "Phan Thiết", "Phúc Yên", "Phú Quốc", "Pleiku", "Quảng Ngãi", "Quy Nhơn", "Sóc Trăng", "Sơn La", "Tam Điệp", "Tam Kỳ", "Tân An", "Thái Bình", "Thái Nguyên", "Thanh Hóa", "Thủ Dầu Một", "Trà Vinh", "Tuyên Quang", "Uông Bí", "Vinh", "Vĩnh Long", "Vĩnh Yên", "Vũng Tàu", "Yên Bái"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);

        btnSearch = findViewById(R.id.btnSearch);
        btnMap = findViewById(R.id.btnMap);
        edtSearch = findViewById(R.id.edtSearch);
        recyclerViewCityList = findViewById(R.id.recyclerViewCityList);

        if(getIntent().getStringExtra("units") != null)
            units = getIntent().getStringExtra("units");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, CITIES);
        edtSearch.setAdapter(adapter);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = getSharedPreferences("CityList3",MODE_PRIVATE);

        fetchCity(units);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchCity();
                flag = true;
            }
        });

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchCity();
                    flag = true;
                    return true;
                }
                return false;
            }
        });


        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CityListActivity.this, MapActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_CITY_BY_MAP);
//                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_ADD_CITY_BY_MAP && resultCode == Activity.RESULT_OK) {
            CityInfoResponse city = new CityInfoResponse(
                    data.getStringExtra("CountryNameChosenByUser"),
                    data.getDoubleExtra("LatChosenByUser",21.0277633),
                    data.getDoubleExtra("LongChosenByUser",105.8341583),
                    data.getStringExtra("CityNameChosenByUser")
            );
            updatePreference(city);
            fetchCity(units);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if(flag || flagDelete){
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
            finish();
            super.onBackPressed();
        }else {
            Intent intent = new Intent();
            setResult(Activity.RESULT_CANCELED, intent);
            finish();
            super.onBackPressed();
        }
    }

    private void fetchCity(String units) {
        edtSearch.getText().clear();
        closeKeyboard();

        Gson gson = new Gson();
        String json = sharedPreferences.getString("CityList3","");
        Type type = new TypeToken<ArrayList<CityInfoResponse>>() {}.getType();
        arrayList = gson.fromJson(json,type);

        CityListAdapter adapter = new CityListAdapter(arrayList,CityListActivity.this, CityListActivity.this, units);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CityListActivity.this);

        recyclerViewCityList.setLayoutManager(layoutManager);
        recyclerViewCityList.setAdapter(adapter);

    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(),0);
            edtSearch.clearFocus();
        }
    }

    private void searchCity() {
        if(edtSearch.getText().toString().isEmpty() || edtSearch.getText().toString().equals("")){
            closeKeyboard();
            return;
        }
        String cityName = edtSearch.getText().toString();

        Call<List<CityInfoResponse>> call = WeatherApiService.weatherApiService.getCityInfo(cityName,"1",API);
        call.enqueue(new Callback<List<CityInfoResponse>>() {
            @Override
            public void onResponse(Call<List<CityInfoResponse>> call, Response<List<CityInfoResponse>> response) {
                if(response.code() == 200){
                    List<CityInfoResponse> cityResponse  = response.body();

                    if (!cityResponse.isEmpty()){
                        updatePreference(cityResponse.get(0));
                        Toast.makeText(CityListActivity.this,"Thêm thành phố thành công!",Toast.LENGTH_LONG).show();
                    }else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CityListActivity.this);
                        builder.setTitle("Tìm kiếm thất bại...!!!");
                        builder.setIcon(R.drawable.ic_baseline_error_outline_24);
                        builder.setMessage("Không tìm thấy thành phố nào! Hãy kiểm tra lại!!.");
                        builder.setCancelable(true);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        builder.create().show();
//                        Toast.makeText(CityListActivity.this,"Không tìm thấy thành phố nào! Hãy kiểm tra lại!!",Toast.LENGTH_LONG).show();
                    }
                    fetchCity(units);
                }
            }

            @Override
            public void onFailure(Call<List<CityInfoResponse>> call, Throwable t) {
                Toast.makeText(CityListActivity.this,"Không có kết quả",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updatePreference(CityInfoResponse city) {
        String addedCity = sharedPreferences.getString("CityList3","");
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Type type = new TypeToken<ArrayList<CityInfoResponse>>() {}.getType();
        arrayList = gson.fromJson(addedCity,type);
        arrayList.add(1, city);
        String json = gson.toJson(arrayList);
        editor.putString("CityList3",json).apply();
    }

    @Override
    public void getFlag(Boolean flag) {
        flagDelete = flag;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent();
        intent.putExtra("position",position);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
