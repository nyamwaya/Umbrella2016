package com.example.aleckson.umbrella.networking;

import com.example.aleckson.umbrella.networking.model.WeatherResults;

import rx.Observable;

/**
 * Created by Aleckson on 12/22/2016.
 *
 */

public interface WeatherInteractor {
    Observable<WeatherResults> fetchWeather(String zipcode);
}
