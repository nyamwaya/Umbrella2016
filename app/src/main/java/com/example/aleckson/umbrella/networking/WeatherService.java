package com.example.aleckson.umbrella.networking;

import android.content.Context;

import com.example.aleckson.umbrella.networking.model.WeatherClient;
import com.example.aleckson.umbrella.networking.model.WeatherResults;

import rx.Observable;

/**
 * Created by Aleckson on 12/22/2016.
 */

public class WeatherService {

    public Observable<WeatherResults> getWeather(String zipcode){
        WeatherClient mWeatherClient = new WeatherClient();
        return mWeatherClient.getWeather(zipcode);
    }
}
