package com.berrakanil.weatherforecast;

import java.util.List;

public class ForecastResponse {
    private List<Weather> forecastList;
    
    public ForecastResponse(List<Weather> list) {
        forecastList = list;
    }
    
    public List<Weather> getForecastList() {
        return forecastList;
    }
}
