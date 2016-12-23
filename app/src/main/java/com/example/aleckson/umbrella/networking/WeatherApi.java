package com.example.aleckson.umbrella.networking;

import com.example.aleckson.umbrella.networking.model.WeatherResults;

import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Aleckson on 12/22/2016.
 */

public interface WeatherApi {
    Observable<WeatherResults> getWeather(@Path("zip") int zipCode);
}
