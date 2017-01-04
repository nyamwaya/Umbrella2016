package com.example.aleckson.umbrella.viewmodel;

import android.util.Log;

import com.example.aleckson.umbrella.IWeather;
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

    public WeatherViewModel(IWeather iWeather) {
        this.iWeather = iWeather;

    }

    public void onResume() {
        getWeather("55376");
    }

    private void getWeather(String zipcode) {
        WeatherResults.fetchWeather(zipcode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherResults>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v(TAG, "Error fetching weather" + e);
                    }

                    @Override
                    public void onNext(WeatherResults weatherResults) {
                        if (weatherResults !=null){
                            Log.v(TAG, "It's" + weatherResults.currentObservation.getTempFahrenheit() + "in"
                                    + weatherResults.currentObservation.getDisplayLocation().getFull());


                            calculateTemp(weatherResults.currentObservation.getTempFahrenheit(),
                                    weatherResults.currentObservation.getTempCelsius());

                            iWeather.setWeather(weatherResults.currentObservation.getWeather());
                            iWeather.setCity(weatherResults.currentObservation.getDisplayLocation().getFull());
                        }


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
