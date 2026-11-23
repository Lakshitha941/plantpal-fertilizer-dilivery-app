package com.s22010022.plantpal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Dashbord extends AppCompatActivity {

    private TextView tvTemperature, tvHumidity, tvRainfall, tvGreeting;
    private ImageButton buttonOrganic, buttonSynthetic, buttonBlends;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(this);
        setContentView(R.layout.activity_dashbord);

        // Greeting
        tvGreeting = findViewById(R.id.tvGreeting);
        if (tvGreeting != null) {
            tvGreeting.setText("Hi, " + sessionManager.getUsername() + "! 👋");
        }

        // Weather TextViews
        tvTemperature = findViewById(R.id.tvTemperature);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvRainfall = findViewById(R.id.tvRainfall);

        // Fertilizer Buttons
        buttonOrganic = findViewById(R.id.buttonOrganic);
        buttonSynthetic = findViewById(R.id.buttonSynthetic);
        buttonBlends = findViewById(R.id.buttonBlends);

        // Weather API Retrofit
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.open-meteo.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            WeatherApi apiService = retrofit.create(WeatherApi.class);
            double latitude = 6.9271;
            double longitude = 79.8612;

            Call<WeatherResponse> call = apiService.getWeather(latitude, longitude, true, "precipitation");
            call.enqueue(new Callback<WeatherResponse>() {
                @Override
                public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        WeatherResponse weather = response.body();
                        if (weather.currentWeather != null) {
                            tvTemperature.setText("Temperature: " + weather.currentWeather.temperature + "°C");
                        }
                        tvHumidity.setText("Humidity: 61%");
                        if (weather.hourly != null && weather.hourly.precipitation != null && weather.hourly.precipitation.length > 0) {
                            tvRainfall.setText("Rainfall: " + weather.hourly.precipitation[0] + "mm");
                        } else {
                            tvRainfall.setText("Rainfall: 0.0mm");
                        }
                    }
                }

                @Override
                public void onFailure(Call<WeatherResponse> call, Throwable t) {
                    tvTemperature.setText("Temperature: 28°C");
                    tvHumidity.setText("Humidity: 65%");
                    tvRainfall.setText("Rainfall: 0.0mm");
                }
            });
        } catch (Exception e) {
            tvTemperature.setText("Temperature: 28°C");
            tvHumidity.setText("Humidity: 65%");
            tvRainfall.setText("Rainfall: 0.0mm");
        }

        // Fertilizer buttons navigation
        buttonOrganic.setOnClickListener(v -> openFertilizerPage1());
        buttonSynthetic.setOnClickListener(v -> openFertilizerPage2());
        buttonBlends.setOnClickListener(v -> openFertilizerPage3());

        // Bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_dashboard) {
                return true;
            } else if (itemId == R.id.navigation_cart) {
                startActivity(new Intent(Dashbord.this, cart.class));
                return true;
            } else if (itemId == R.id.navigation_setting) {
                startActivity(new Intent(Dashbord.this, setting.class));
                return true;
            } else if (itemId == R.id.navigation_profile) {
                startActivity(new Intent(Dashbord.this, account.class));
                return true;
            }
            return false;
        });
    }

    private void openFertilizerPage1() {
        startActivity(new Intent(Dashbord.this, fertilizerPage1.class));
    }

    private void openFertilizerPage2() {
        startActivity(new Intent(Dashbord.this, fertilizerPage2.class));
    }

    private void openFertilizerPage3() {
        startActivity(new Intent(Dashbord.this, fertilizerPage3.class));
    }
}
