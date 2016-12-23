package com.example.aleckson.umbrella.networking.model;

import com.example.aleckson.umbrella.networking.WeatherApi;

import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by kerub on 12/22/2016.
 */

public class WeatherService implements WeatherApi {

    @Override
    public Observable<WeatherResults> getWeather(@Path("zip") int zipCode) {
        return null;
    }
}
