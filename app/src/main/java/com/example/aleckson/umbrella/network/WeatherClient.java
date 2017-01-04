package com.example.aleckson.umbrella.network;

import com.example.aleckson.umbrella.model.WeatherResults;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Aleckson on 12/22/2016.
 *
 * This class is a model in MVVM pattern
 */

public class WeatherClient {

    private WeatherService service;

    //Retrofit implementation
    public WeatherClient() {

        // Configure Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                // Base URL can change for endpoints (dev, staging, live..)
                .baseUrl("http://api.wunderground.com/api/")
                // Takes care of converting the JSON response into java objects
                .addConverterFactory(GsonConverterFactory.create())
                // Retrofit Call to RxJava Observable
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        // Create the Google Book API Service
        service = retrofit.create(WeatherService.class);
    }



    public Observable<WeatherResults> fetchWeather(String zipcode) {
        return service.fetchWeather(zipcode).subscribeOn(Schedulers.io());
    }
}
