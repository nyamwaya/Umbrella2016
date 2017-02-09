package com.example.aleckson.umbrella.viewmodel;

import android.util.Log;

import com.example.aleckson.umbrella.IWeather;
import com.example.aleckson.umbrella.model.CurrentObservation;
import com.example.aleckson.umbrella.model.Day;
import com.example.aleckson.umbrella.model.DayConditions;
import com.example.aleckson.umbrella.model.ForecastCondition;
import com.example.aleckson.umbrella.model.WeatherResults;

import java.util.ArrayList;
import java.util.List;

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

    //sending the appropariet information to main activity to be displayed via Interface t
    private void calculateCurrentConditions(CurrentObservation currentObservation) {
        if (currentObservation != null) {
            //Log to know that we are doing thighs right
            Log.v(TAG, "Setting current conditions. Its: " + currentObservation.getTempFahrenheit() + "in " + currentObservation.getDisplayLocation().getFull());

            calculateTemp(currentObservation.getTempFahrenheit(),
                    currentObservation.getTempFahrenheit());

            //Passing the weather status to the main activity using an interface so it can be displayed.
            iWeather.setCity(currentObservation.getDisplayLocation().getFull());
            //Passing the location to main activity so it can be displayed.
            iWeather.setWeather(currentObservation.getWeather());

        } else
            //Log to know something went wrong
            Log.v(TAG, "Failed to set current conditions");
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
                        if (weatherResults != null) {
                            Log.v(TAG, "10 day forecast is not null");
                            //passing the data to a method that specializes in parsing the data
                            /* calculateTenDayForecast(weatherResults.forecast);*/

                            List<ForecastCondition> hourlyConditions = weatherResults.forecast;
                            long yday = 0;
                            List<Day> days = new ArrayList<>();
                            for(ForecastCondition hourlyCon : hourlyConditions){
                                if(yday<hourlyCon.yday){
                                    yday = hourlyCon.yday;
                                    Day day = new Day();
                                    day.dayName = hourlyCon.day;
                                    day.yday = hourlyCon.yday;
                                    day.conditions = new ArrayList<>();
                                    setDayConditions(hourlyConditions, yday, day);
                                    days.add(day);
                                }
                            }
                            Log.v(TAG, "The days are: " + days);
                            iWeather.setHourlyConitions(days, metricMode);
                        }
                    }
                });

    }

    //Calculates the individual day's condition
    private void setDayConditions(List<ForecastCondition> hourlyConditions, long yday, Day day) {
        //loop through the hourly conditions data
        for(int i=0; i<hourlyConditions.size(); i++){
            ForecastCondition forecastCondition = hourlyConditions.get(i);
            //the Year data should always equal the forecast conditions year data.
            //Here we are setting the conditions for a given day.
            if(forecastCondition.yday == yday) {
                //Since this is wehre we are setting the individial day conditions,
                // This is where we should also find the max and min temp of the day
                DayConditions dayConditions = new DayConditions();
                dayConditions.condition = forecastCondition.condition;
                dayConditions.tempC = Math.round(forecastCondition.tempCelsius);
                dayConditions.tempF = Math.round(forecastCondition.tempFahrenheit);
                dayConditions.time = forecastCondition.displayTime;
                day.conditions.add(dayConditions);
            }
            if(day.conditions.size()==8){
                break;
            }
        }
        // setLowAndHighTemp(hourlyConditions, day);
    }


    //The logic for displaying the current temp in the header
    //then we send it to main activity via interface
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
