package com.example.aleckson.umbrella;

/**
 * Created by kerub on 1/3/2017.
 */

public interface IWeather {
    void setTemp(String s);
    void setWeather(String weather);
    void setAppColor(int temp, int basetemp);
    void setCity(String full);
}
