package com.example.aleckson.umbrella.viewmodel;

import com.example.aleckson.umbrella.networking.WeatherApi;
import com.example.aleckson.umbrella.networking.model.WeatherResults;

import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by Aleckson on 12/22/2016.
 * 
 * This ViewModel needs two parameters: The WeatherAPI and the Main Thread scheduler.
 *
 * If you are formatting data, this is the place to do it, you can
 * use RxJava map methods to transform one object to another
 *
 */

public class WeatherViewModel {

    private WeatherApi api;
    private Scheduler scheduler;

    public WeatherViewModel(WeatherApi api, Scheduler scheduler) {
        this.api = api;
        this.scheduler = scheduler;
    }

    //Make sure that we are using the right thread.
    public Observable<WeatherResults> getWeather(String zipcode) {
        return api.getWeather(zipcode).observeOn(scheduler);
    }
}
