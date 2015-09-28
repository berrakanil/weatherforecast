package com.berrakanil.weatherforecast;

import java.util.List;
import java.util.Map;

public interface IWeatherRetriever {
    public List<Weather> getForeCast(Map<String, String> forecast) throws Exception;
}