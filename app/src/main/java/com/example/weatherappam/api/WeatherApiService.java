package com.example.weatherappam.api;

import com.example.weatherappam.model.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {

    // Βάζουμε όλες τις σταθερές παραμέτρους απευθείας στο URL
    @GET("v1/forecast?current=temperature_2m,weather_code,wind_speed_10m&daily=temperature_2m_max,temperature_2m_min,weather_code&hourly=temperature_2m,weather_code&timezone=auto&forecast_days=5")
    Call<WeatherResponse> getWeather(
            @Query("latitude")  double latitude,
            @Query("longitude") double longitude
    );
}