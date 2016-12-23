package com.example.aleckson.umbrella.networking.model;

import java.util.Date;

/**
 * Created by Aleckson on 12/23/2016.
 *
 * Represents a forecast weather condition returned from Weather Underground
 *
 * Does not include all available only data- only potentially useful fields are included
 *
 */
public class ForecastCondition {
    /**
     * Formatted time suitable for display
     */
    private String displayTime;

    /**
     * Date representation of the time associated with this forecast
     */
    private Date time;

    /**
     * The icon to use for this reading
     */
    private String icon;

    /**
     * The human-readable name of the condition
     */
    private String condition;

    /**
     * The temperature that is forecast (in degrees Fahrenheit)
     */
    private float tempFahrenheit;

    /**
     * The temperature that is forecast (in degrees Celsius)
     */
    private float tempCelsius;



    /**
     * Getters and setters for proper encapsulation
     */

    public String getDisplayTime() {
        return displayTime;
    }

    public void setDisplayTime(String displayTime) {
        this.displayTime = displayTime;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
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
}
