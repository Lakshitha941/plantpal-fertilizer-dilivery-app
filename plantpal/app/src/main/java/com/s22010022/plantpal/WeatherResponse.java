package com.s22010022.plantpal;
import com.google.gson.annotations.SerializedName;

public class WeatherResponse {

    @SerializedName("current_weather")
    public CurrentWeather currentWeather;

    @SerializedName("hourly")
    public Hourly hourly;

    public static class CurrentWeather {
        public double temperature;

        // Open-Meteo current_weather does not return humidity, so we can use placeholder
    }

    public static class Hourly {
        @SerializedName("precipitation")
        public double[] precipitation;

        @SerializedName("time")
        public String[] time;
    }
}
