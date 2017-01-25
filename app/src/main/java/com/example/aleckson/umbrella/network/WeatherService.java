package com.example.aleckson.umbrella.network;

import com.example.aleckson.umbrella.model.CurrentObservation;
import com.example.aleckson.umbrella.model.WeatherResults;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Aleckson on 12/22/2016.
 *
 * Retrofit interface
 */

public interface WeatherService {

    /**
     * Get the current conditions and that day's hourly conditions for a given zipcode
     */
    @GET("9375b1bed0c9c10b/conditions/hourly/q/{zipCode}.json")
    Observable<CurrentObservation> getCurrentConditions(@Path("zipCode") String zipCode);

    /**
     * Get the hourly 10day forecast for a given zip code
     */
    @GET("9375b1bed0c9c10b/conditions/hourly10day/q/{zip}.json")
    Observable<WeatherResults> getTenDayHourly(@Path("zip") String zipCode);

}
