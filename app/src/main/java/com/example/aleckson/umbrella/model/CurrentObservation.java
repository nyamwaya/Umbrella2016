package com.example.aleckson.umbrella.model;

import com.example.aleckson.umbrella.network.WeatherClient;
import com.google.gson.annotations.SerializedName;

import rx.Observable;

/**
 * Created by Aleckson on 12/23/2016.
 *
 * Represents the "current_observation" data returned from Weather Underground
 *
 * Does not include all available only data- only potentially useful fields are included
 *
 */
public class CurrentObservation {

    @SerializedName("display_location")
    private DisplayLocation displayLocation;

    @SerializedName("temp_f")
    private float tempFahrenheit;

    @SerializedName("temp_c")
    private float tempCelsius;

    private String weather;

    private String icon;

    public DisplayLocation getDisplayLocation() {
        return displayLocation;
    }

    public void setDisplayLocation(DisplayLocation displayLocation) {
        this.displayLocation = displayLocation;
    }

    public float getTempFahrenheit() {
        return tempFahrenheit;
    }

    public void setTempFahrenheit(float tempFahrenheit) {
        this.tempFahrenheit = tempFahrenheit;
    }

    public float getTempCelsius() {
        return tempCelsius;
    }

    public void setTempCelsius(float tempCelsius) {
        this.tempCelsius = tempCelsius;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public static Observable<CurrentObservation> fetchCurrentObseervations(String zipcode){
        WeatherClient client = new WeatherClient();
        return client.fetchCurrentObservations(zipcode);
    }
}
