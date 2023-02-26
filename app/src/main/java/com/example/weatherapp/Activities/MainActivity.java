package com.example.weatherapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.weatherapp.Adapter.FragmentPageAdapter;
import com.example.weatherapp.Fragments.WeatherScreenFragment;
import com.example.weatherapp.Models.CityInfoResponse;
import com.example.weatherapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;



public class MainActivity extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_CODE_ADD_CITY = 10;
    SharedPreferences sharedPreferences, unitsharedPreferences;
    FusedLocationProviderClient fusedLocationProviderClient;
    Geocoder geocoder;
    LocationRequest mLocationRequest;
    LocationManager locationManager;
    LocationCallback mLocationCallback;
    ProgressDialog progressDialog;
    List<CityInfoResponse> arrayList;

    Boolean gpsStatus = false;
    Double lat = null;
    Double lon = null;
    String city = null;
    String country = null;
    String units ;

    ViewPager2 viewPager;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isNetworkConnected() ) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Không có kết nối Internet");
            builder.setMessage("Vui lòng kết nối Internet để sử dụng ứng dụng");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Do something when "OK" button is clicked
                }
            });
            builder.show();
        }
        unitsharedPreferences = getSharedPreferences("Units", MODE_PRIVATE);
        units = unitsharedPreferences.getString("units","metric");
        viewPager = findViewById(R.id.ViewPagerScreen);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.action_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
//                        startActivity(new Intent(CurrentActivity.this, HomeActivity.class));
                        return true;
                    case R.id.action_news:
                        startActivity(new Intent(MainActivity.this, NewsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.action_settings:
                        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
//        if(getIntent().getStringExtra("units") != null)
//        units = getIntent().getStringExtra("units");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        geocoder = new Geocoder(this, Locale.getDefault());

//        if (isNetworkConnected()){
//            if(!isInternetAvailable()){
//                return;
//            }
//        }else {
//            return;
//        }

//        mLocationRequest = LocationRequest.create();
//        mLocationRequest.setInterval(10000);
//        mLocationRequest.fastestInterval = 5000;
//        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY;
        mLocationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(5000)
                .build();
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        lat = location.getLatitude();
                        lon = location.getLongitude();
                        List<Address>  address = null;
                        try {
                            address = geocoder.getFromLocation(lat, lon, 1);
                            if (address.get(0).getLocality() == null) city = address.get(0).getAdminArea();
                            else address.get(0).getLocality();
                            country = address.get(0).getCountryCode();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Log.d("lat1", lat.toString());
                        Log.d("lon1", lon.toString());
                        Log.d("city1", city);
                        Log.d("country1", country);
                        sharedPrefCall();
                        if (fusedLocationProviderClient != null) {
                            fusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
                        }
                    }
                }
            }
        };
//        weatherScreenFragment = WeatherScreenFragment.newInstance();
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.add(R.id.ViewPagerScreen,weatherScreenFragment);
//        ft.commit();


//        FragmentStateAdapter pageAdapter = new FragmentPageAdapter(this, arrayList);
//        viewPager.setAdapter(pageAdapter);

//        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, CityListActivity.class);
//                startActivity(intent);
//            }
//        });

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSearchbar:
                Intent intent = new Intent(MainActivity.this, CityListActivity.class);
                intent.putExtra("units", units);
                startActivityForResult(intent,REQUEST_CODE_ADD_CITY);
//                Toast.makeText(this, "update clicked", Toast.LENGTH_SHORT).show();
//                Log.v("TaG","click plus");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_ADD_CITY && resultCode == Activity.RESULT_OK) {
            sharedPrefCall();

            int pos = data.getIntExtra("position",0);
            viewPager.setCurrentItem(pos);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            lastLocation();
        } else {
            askPermission();
        }
    }

    private void askPermission() {
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)
            ){
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        },
                        LOCATION_PERMISSION_REQUEST_CODE
                );
            }else{
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION
                        },
                        LOCATION_PERMISSION_REQUEST_CODE
                );
            }

        }
    }

    private void lastLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        gpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!gpsStatus) {
            new AlertDialog.Builder(this)
                    .setTitle("Hãy bật GPS")
                    .setMessage("Chọn Yes để bật GPS")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent1);
                        }
                    })
                    .setCancelable(false)
                    .create()
                    .show();
        } else {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMax(100);
            progressDialog.setMessage("Đang tải....");
            progressDialog.setTitle("Lấy dữ liệu thời tiết");
            progressDialog.show();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            askPermission();
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();

        task.addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    lat = location.getLatitude();
                    lon = location.getLongitude();
                    List<Address> address ;
                    try {
                        address = geocoder.getFromLocation(lat, lon, 1);
                        if (address != null){
                            country = address.get(0).getCountryName();
                            if ( address.get(0).getLocality() != null) city = address.get(0).getLocality();
                            else if(address.get(0).getAdminArea() != null) city = address.get(0).getAdminArea();
                            else {
                                city = "Hà Nội";
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("Không lấy được dữ liệu vị trí hiện tại...!!!");
                                builder.setIcon(R.drawable.ic_baseline_error_outline_24);
                                builder.setMessage("Không tìm thấy thành phố nào! Hãy kiểm tra lại!!.");
                                builder.setCancelable(true);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                                builder.create().show();
                            }
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Không lấy được dữ liệu address hiện tại...!!!");
                            builder.setIcon(R.drawable.ic_baseline_error_outline_24);
                            builder.setMessage("Không tìm thấy thành phố nào! Hãy kiểm tra lại!!.");
                            builder.setCancelable(true);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            builder.create().show();
                            city = "Sapa";
                            country = "VN";
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d("lat2", lat.toString());
                    Log.d("lon2", lon.toString());
                    Log.d("city2", city);
                    Log.d("country2", country);

                    sharedPrefCall();
                } else {
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        askPermission();
                    }
                    LocationServices.getFusedLocationProviderClient(MainActivity.this)
                            .requestLocationUpdates(mLocationRequest, mLocationCallback, null);

                }
            }
        });
    }

    private void sharedPrefCall() {
        progressDialog.dismiss();
        sharedPreferences = getSharedPreferences("CityList3", AppCompatActivity.MODE_PRIVATE);
//        sharedPreferences.edit().clear().apply()
        String prefData = sharedPreferences.getString("CityList3","");
        Gson gson = new Gson();
        SharedPreferences.Editor editor  = sharedPreferences.edit();
//        val type = object : TypeToken<List<CityInfoResponse?>?>() {}.type;
        Type type = new TypeToken<ArrayList<CityInfoResponse>>() {}.getType();
//        List<myClass> objectsList = new Gson().fromJson(json, typeOfObjectsList);

        if(prefData.isEmpty()){
            arrayList = new ArrayList<CityInfoResponse>();
        } else{
            arrayList = gson.fromJson(prefData,type);
        }

        if(country!= null && lat != null && lon != null && city != null){
            if(arrayList.isEmpty()){
                arrayList.add(new CityInfoResponse(country,lat,lon,city));
            }
            if(arrayList.get(0).getName() != city){
                arrayList.set(0, new CityInfoResponse(country, lat, lon, city));
            }
        }
        Log.d("lat",lat.toString());
        Log.d("lon",lon.toString());
        Log.d("city",city);
        Log.d("country",country);

        Log.d("ArraylistFetched",arrayList.toString());
        String json = gson.toJson(arrayList);
        editor.putString("CityList3", json).apply();

        FragmentPageAdapter pageAdapter  = new FragmentPageAdapter(this,arrayList, units);
        viewPager.setAdapter(pageAdapter);
    }
//    public void onAddCityClick(MenuItem item) {
////        Log.d("MenuItem", "onLogOutClick :: "+item.getItemId());
//        Intent intent = new Intent(MainActivity.this, CityListActivity.class);
//        startActivity(intent);
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length != 0 && requestCode == LOCATION_PERMISSION_REQUEST_CODE){
            for(int i=0; i < grantResults.length ; i++){
                if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                    askPermission();
                }
            }
            onStart();
        }
    }
}