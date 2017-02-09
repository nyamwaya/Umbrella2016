package com.example.aleckson.umbrella.viewmodel;

import android.util.Log;

import com.example.aleckson.umbrella.IWeather;
import com.example.aleckson.umbrella.model.CurrentObservation;
import com.example.aleckson.umbrella.model.WeatherResults;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Aleckson on 12/22/2016.
 * <p>
 * The job of this View Holder is to make sure that we are using the right thread.
 * It needs two parameters: The WeatherAPI and the Main Thread scheduler.
 * <p>
 * If you are formatting data, this is the place to do it, you can
 * use RxJava map methods to transform one object to another
 */

public class WeatherViewModel {

    private static final String TAG = WeatherViewModel.class.getSimpleName();
    private IWeather iWeather;
    private boolean metricMode = false;
    private String mUserZipCode = "55428";

    public WeatherViewModel(IWeather iWeather) {
        this.iWeather = iWeather;

    }

    public void onResume() {
       fetchCurrentConditions(mUserZipCode);
    }

    //Responsible for requesting current conditions data
    private void fetchCurrentConditions(String zipcode){
        CurrentObservation.fetchCurrentObseervations(zipcode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CurrentObservation>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CurrentObservation currentObservation) {
                        if (currentObservation != null) {
                            Log.v(TAG, "Current conditions data fetched successfully");
                            //Our data came back not null!!! yaya! now we must
                            //pass the data to ites respective methods so we maintain single responsibility.
                            // getHourlyCondition(weatherResults.forecast);
                            calculateCurrentConditions(currentObservation);
                            fetchTenDayHourly(mUserZipCode);
                        }

                    }
                });

    }

    private void calculateCurrentConditions(CurrentObservation currentObservation) {
        
    }

    //Responsible for requesting 10 day hourly forecast
    private void fetchTenDayHourly(String zipcode){
        WeatherResults.fetchTenDayHourly(zipcode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherResults>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(WeatherResults weatherResults) {

                    }
                });

    }
    

    private void calculateTemp(float tempFahrenheit, float tempCelsius) {
        Log.v(TAG, "F" + tempFahrenheit + "C" + tempCelsius);
        int temp;
        int basetemp = 60;

        if (metricMode) {
            basetemp = ((5 * (60 - 32)) / (9));
            Log.v(TAG, "basetemp metric: " + basetemp);
            temp = Math.round(tempCelsius);
        } else {
            temp = Math.round(tempFahrenheit);
        }

        iWeather.setTemp(String.valueOf(temp) + (char) 0x00B0);
        iWeather.setAppColor(temp, basetemp);

    }

}
