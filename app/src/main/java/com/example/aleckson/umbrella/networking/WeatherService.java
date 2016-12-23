package com.example.aleckson.umbrella.networking;

import com.example.aleckson.umbrella.networking.model.WeatherResults;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Aleckson on 12/22/2016.
 *
 * Retrofit interface
 */

public interface WeatherService {
    @GET("9375b1bed0c9c10b/conditions/hourly/q/{zipCode}.json")
    Observable<WeatherResults> fetchWeather(@Path("zipCode") String zipCode);
}
