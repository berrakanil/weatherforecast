package com.berrakanil.weatherforecast;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestHandler {
    
    private IWeatherRetriever weatherRetriever;
    
    public RequestHandler() {
        weatherRetriever = new YahooWeatherRetriever();
    }
    
    public RequestHandler(IWeatherRetriever weatherRetriever) {
        this.weatherRetriever = weatherRetriever;
    }
    
    @RequestMapping("/forecast")
    public ForecastResponse getForecast(@RequestParam Map<String,String> forecast) throws Exception {
        List<Weather> forecastList = weatherRetriever.getForeCast(forecast);
        return new ForecastResponse(forecastList);        
    }
}
