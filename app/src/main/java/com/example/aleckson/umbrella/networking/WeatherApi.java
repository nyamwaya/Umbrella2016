package com.example.aleckson.umbrella.networking;

import com.example.aleckson.umbrella.networking.model.WeatherResults;

import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Aleckson on 12/22/2016.
 *
 * This is considered a model in MVVM architecture
 *
 * Interface that will provide a method to access to the wunderground weather api
 * It only contains the definition of the methods available in the Model and
 * should be kept as simple as possible.
 */

public interface WeatherApi {
    Observable<WeatherResults> getWeather(@Path("zip") String zipCode);
}
