package com.example.weatherapp.Activities;

import static com.example.weatherapp.Constant.API;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.weatherapp.API.NewsApiService;
import com.example.weatherapp.API.WeatherApiService;
import com.example.weatherapp.Adapter.CityListAdapter;
import com.example.weatherapp.Adapter.ForecastAdapter;
import com.example.weatherapp.Adapter.HourlyForecastAdapter;
import com.example.weatherapp.Adapter.NewsListAdapter;
import com.example.weatherapp.Models.Daily;
import com.example.weatherapp.Models.ForecastResponse;
import com.example.weatherapp.Models.Hourly;
import com.example.weatherapp.Models.News;
import com.example.weatherapp.NewsWebClickedListener;
import com.example.weatherapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsActivity extends AppCompatActivity implements NewsWebClickedListener {

    BottomNavigationView bottomNavigationView;
    List<News> newsList = new ArrayList<>();
    RecyclerView recyclerViewNewsList;
    ProgressDialog progressDialog;
    SwipeRefreshLayout swipeRefreshLayout;
    NewsListAdapter adapter;
    String BASE_URL1  = "http://10.0.2.2:5000/";
    Gson gson;
    OkHttpClient okHttpClient;
    int cacheSize = 10 * 1024 * 1024;
    Cache cache;// 10 MB

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        recyclerViewNewsList = findViewById(R.id.recyclerViewNewsList);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Gọi API để lấy dữ liệu mới
                fetchDataFromApi();
            }
        });

//        newsList.add(new News("https://www.24h.com.vn/tin-tuc-trong-ngay/khong-khi-lanh-tran-ve-mien-bac-co-tai-dien-mua-phun-nom-am-c46a1443361.html","https://cdn.24h.com.vn/upload/1-2023/images/2023-02-23/Khong-khi-lanh-tran-ve-mien-Bac-co-tai-dien-mua-phun-nom-am-amh-1677153988-281-width660height440.jpg","Không khí lạnh tràn về, miền Bắc có tái diễn mưa phùn, nồm ẩm?","23/02/2023"));
//        newsList.add(new News("https://www.24h.com.vn/tin-tuc-trong-ngay/khong-khi-lanh-tran-ve-mien-bac-co-tai-dien-mua-phun-nom-am-c46a1443361.html","https://cdn.24h.com.vn/upload/1-2023/images/2023-02-23/Khong-khi-lanh-tran-ve-mien-Bac-co-tai-dien-mua-phun-nom-am-amh-1677153988-281-width660height440.jpg","Không khí lạnh tràn về, miền Bắc có tái diễn mưa phùn, nồm ẩm?","23/02/2023"));
//        newsList.add(new News("https://www.24h.com.vn/tin-tuc-trong-ngay/khong-khi-lanh-tran-ve-mien-bac-co-tai-dien-mua-phun-nom-am-c46a1443361.html","https://cdn.24h.com.vn/upload/1-2023/images/2023-02-23/Khong-khi-lanh-tran-ve-mien-Bac-co-tai-dien-mua-phun-nom-am-amh-1677153988-281-width660height440.jpg","Không khí lạnh tràn về, miền Bắc có tái diễn mưa phùn, nồm ẩm?","23/02/2023"));
//        newsList.add(new News("https://www.24h.com.vn/tin-tuc-trong-ngay/khong-khi-lanh-tran-ve-mien-bac-co-tai-dien-mua-phun-nom-am-c46a1443361.html","https://cdn.24h.com.vn/upload/1-2023/images/2023-02-23/Khong-khi-lanh-tran-ve-mien-Bac-co-tai-dien-mua-phun-nom-am-amh-1677153988-281-width660height440.jpg","Không khí lạnh tràn về, miền Bắc có tái diễn mưa phùn, nồm ẩm?","23/02/2023"));
//        newsList.add(new News("https://www.24h.com.vn/tin-tuc-trong-ngay/khong-khi-lanh-tran-ve-mien-bac-co-tai-dien-mua-phun-nom-am-c46a1443361.html","https://cdn.24h.com.vn/upload/1-2023/images/2023-02-23/Khong-khi-lanh-tran-ve-mien-Bac-co-tai-dien-mua-phun-nom-am-amh-1677153988-281-width660height440.jpg","Không khí lạnh tràn về, miền Bắc có tái diễn mưa phùn, nồm ẩm?","23/02/2023"));
//        newsList.add(new News("https://www.24h.com.vn/tin-tuc-trong-ngay/khong-khi-lanh-tran-ve-mien-bac-co-tai-dien-mua-phun-nom-am-c46a1443361.html","https://cdn.24h.com.vn/upload/1-2023/images/2023-02-23/Khong-khi-lanh-tran-ve-mien-Bac-co-tai-dien-mua-phun-nom-am-amh-1677153988-281-width660height440.jpg","Không khí lạnh tràn về, miền Bắc có tái diễn mưa phùn, nồm ẩm?","23/02/2023"));

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.action_news);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        startActivity(new Intent(NewsActivity.this, MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.action_news:
//                        startActivity(new Intent(getApplicationContext(), NewsActivity.class));
                        return true;
                    case R.id.action_settings:
                        startActivity(new Intent(NewsActivity.this, SettingsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Đang tải....");
        progressDialog.setTitle("Lấy dữ liệu các bài báo");
        progressDialog.show();


        cache = new Cache(this.getCacheDir(), cacheSize);

        okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setLenient()
                .create();

//        // Khởi tạo OkHttpClient với cache
//        Cache cache = new Cache(getCacheDir(), cacheSize);
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .cache(cache)
//                .build();
//
//        // Khởi tạo Retrofit với OkHttpClient đã khởi tạo ở trên
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL1)
//                .client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();
//
//        // Khởi tạo interface MyApiService với Retrofit đã khởi tạo ở trên
//        NewsApiService apiService = retrofit.create(NewsApiService.class);
//
//        // Lấy dữ liệu từ cache
//        Response<List<News>> cachedResponse = okHttpClient
//                .cache().get(new Request.Builder().url(BASE_URL1).build());
//
//        if (cachedResponse != null && cachedResponse.body() != null) {
//            String cachedData = cachedResponse.body().string();
//            // Xử lý dữ liệu và cập nhật giao diện của RecyclerView ở đây
//        }
        Retrofit retrofit  = new  Retrofit.Builder()
                .baseUrl(BASE_URL1)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        NewsApiService newsApiService = retrofit.create(NewsApiService.class);
        Call<List<News>> call = newsApiService.getNewsData();
        call.timeout().timeout(30, TimeUnit.SECONDS);

        call.enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if(response.code() == 200){
                    newsList = response.body();

                    progressDialog.dismiss();
                    adapter = new NewsListAdapter(newsList, NewsActivity.this);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(NewsActivity.this);

                    recyclerViewNewsList.setLayoutManager(layoutManager);
                    recyclerViewNewsList.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("ERROR","Call Api error" + t.getMessage() );
                Toast.makeText(NewsActivity.this,
                        "Call Api error" + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void onItemNewsClicked(int position) {
        String url = newsList.get(position).getUrl();

        Intent intent = new Intent(NewsActivity.this, webViewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    private void fetchDataFromApi() {
        // Gọi API để lấy dữ liệu mới
        Retrofit retrofit  = new  Retrofit.Builder()
                .baseUrl(BASE_URL1)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        NewsApiService newsApiService = retrofit.create(NewsApiService.class);
        Call<List<News>> call = newsApiService.getNewsData();
        call.timeout().timeout(30, TimeUnit.SECONDS);
        call.enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if(response.code() == 200){
                    newsList = response.body();
                    // Sau khi lấy được dữ liệu mới, cập nhật lại danh sách trong adapter
//                    adapter.setData(newsList);
                    adapter = new NewsListAdapter(newsList, NewsActivity.this);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(NewsActivity.this);

                    recyclerViewNewsList.setLayoutManager(layoutManager);
                    recyclerViewNewsList.setAdapter(adapter);
                    // Ẩn icon loading của SwipeRefreshLayout
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                Log.e("ERROR","Call Api error" + t.getMessage() );
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(NewsActivity.this,
                        "Call Api error" + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });


    }
}