package com.example.aleckson.umbrella.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleckson on 2/9/2017.
 * Represents a Day
 */
public class Day {
    public String dayName;
    public List<DayConditions> conditions;
    public Long yday;
    private int highestTemp;
    private int lowestTemp;

    public int getHighestTemp() {
        return highestTemp;
    }

    public void setHighestTemp(int highestTemp) {
        this.highestTemp = highestTemp;
    }

    public int getLowestTemp() {
        return lowestTemp;
    }

    public void setLowestTemp(int lowestTemp) {
        this.lowestTemp = lowestTemp;
    }

}
