package com.example.aleckson.umbrella.networking;

import android.content.Context;

import com.example.aleckson.umbrella.networking.model.WeatherClient;
import com.example.aleckson.umbrella.networking.model.WeatherResults;

import rx.Observable;

/**
 * Created by kerub on 12/22/2016.
 */

public class WeatherService {

    public Observable<WeatherResults> getWeather(int zipcode){
        WeatherClient mWeatherClient = new WeatherClient();
        return mWeatherClient.getWeather(zipcode);
    }
}
