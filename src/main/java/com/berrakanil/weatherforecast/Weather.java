package com.berrakanil.weatherforecast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Weather {
    
    private Date date;
    private String weatherText;
    private String day;
    private int    highTemp;
    private int    lowTemp;
    
    public Weather(Date date, String day, int highTemp, int lowTemp, String weatherText) {
        this.date        = date;
        this.day         = day;
        this.highTemp    = highTemp;
        this.lowTemp     = lowTemp;
        this.weatherText = weatherText;
    }
    
    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        return dateFormat.format(date);
    }
    
    public String getDay() {
        return day;
    }
    
    public int getHighTemperature() {
        return highTemp;
    }
    
    public int getLowTemperature() {
        return lowTemp;
    }
    
    public String getWeatherText() {
        return weatherText;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        final Weather that = (Weather) o;
        if (!day.equals(that.day) || 
            highTemp != that.highTemp || 
            lowTemp != that.lowTemp || 
            !weatherText.equals(that.weatherText) || 
            !getDate().equals(that.getDate())) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public String toString() {
        String template = "(Date: [%s], weatherText: [%s], day: [%s], highTemp: [%d], lowTemp: [%d])";
        String string = String.format(template, getDate(), weatherText, day, highTemp, lowTemp);
        return string;
    }
}
