package com.example.aleckson.umbrella;

import com.example.aleckson.umbrella.model.Day;

import java.util.List;

/**
 * Created by kerub on 1/3/2017.
 */

public interface IWeather {
    void setTemp(String s);
    void setWeather(String weather);
    void setAppColor(int temp, int basetemp);
    void setCity(String full);

    void setHourlyConitions(List<Day> days, boolean metricMode);
}
