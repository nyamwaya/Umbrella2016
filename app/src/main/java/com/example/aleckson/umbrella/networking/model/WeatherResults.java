package com.example.aleckson.umbrella.networking.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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
}
