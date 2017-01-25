package com.example.aleckson.umbrella.network;

import com.example.aleckson.umbrella.model.CurrentObservation;
import com.example.aleckson.umbrella.model.ForecastCondition;
import com.example.aleckson.umbrella.model.WeatherResults;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Scheduler;
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

        //Gson converter so we can use our custom parser for hourly forecast.
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ForecastCondition.class, new ForecastParser())
                .create();


        // Configure Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                // Base URL can change for endpoints (dev, staging, live..)
                .baseUrl("http://api.wunderground.com/api/")
                // Takes care of converting the JSON response into java objects
                .addConverterFactory(GsonConverterFactory.create(gson))
                // Retrofit Call to RxJava Observable
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        // Create the Google Book API Service
        service = retrofit.create(WeatherService.class);
    }



    public Observable<CurrentObservation> fetchCurrentObservations(String zipcode) {
        return service.getCurrentConditions(zipcode).subscribeOn(Schedulers.io());
    }

    public Observable<WeatherResults> fetchTenDayHouly(String zipcode){
        return service.getTenDayHourly(zipcode).subscribeOn(Schedulers.io());
    }
}
