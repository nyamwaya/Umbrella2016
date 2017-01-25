package com.example.aleckson.umbrella.model;

import com.example.aleckson.umbrella.network.WeatherClient;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import rx.Observable;

/**
 *
 * Created by Aleckson on 12/22/2016.
 *
 * Represents weather information returned from the Weather Underground API
 *
 * Does not include all available only data- only potentially useful fields are included
 *
 */
public class WeatherResults {

    @SerializedName("current_observation")
    public CurrentObservation currentObservation;

    @SerializedName("hourly_forecast")
    public List<ForecastCondition> forecast;

    public static Observable<WeatherResults> fetchTenDayHourly(String zipcode){
        WeatherClient client = new WeatherClient();
        return client.fetchTenDayHouly(zipcode);
    }
}
