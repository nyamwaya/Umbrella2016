package com.example.aleckson.umbrella.networking.model;

import com.example.aleckson.umbrella.networking.WeatherApi;
import com.example.aleckson.umbrella.networking.WeatherService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Aleckson on 12/22/2016.
 *
 * This class is a model in MVVM pattern
 */

public class WeatherClient implements WeatherApi {

    private WeatherService service;

    //Retrofit implementation
    public WeatherClient() {
        // Configure Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                // Base URL can change for endpoints (dev, staging, live..)
                .baseUrl("https://www.googleapis.com")
                // Takes care of converting the JSON response into java objects
                .addConverterFactory(GsonConverterFactory.create())
                // Retrofit Call to RxJava Observable
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        // Create the Google Book API Service
        service = retrofit.create(WeatherService.class);
    }

    @Override
    public Observable<WeatherResults> getWeather(@Path("zip") int zipCode) {
        //network calls should me made in the I/O thread
        return service.getWeather(zipCode).subscribeOn(Schedulers.io());
    }
}
