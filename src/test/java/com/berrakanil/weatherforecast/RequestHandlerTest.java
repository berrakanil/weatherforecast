package com.berrakanil.weatherforecast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import junit.framework.TestCase;

public class RequestHandlerTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    @Test
    public void testGetForecast() throws Exception {
        IWeatherRetriever mockWeatherRetriever = mock(IWeatherRetriever.class);
        RequestHandler requestHandler = new RequestHandler(mockWeatherRetriever);
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        
        List<Weather> forecastList = new ArrayList<Weather>();
        Date date = dateFormat.parse("27 Sep 2015");
        forecastList.add(new Weather(date, "Sun", 29, 15, "Mostly Clear"));
        date = dateFormat.parse("28 Sep 2015");
        forecastList.add(new Weather(date, "Mon", 31, 16, "Partly Cloudy"));
        date = dateFormat.parse("29 Sep 2015");
        forecastList.add(new Weather(date, "Tue", 27, 13, "Partly Cloudy"));
        date = dateFormat.parse("30 Sep 2015");
        forecastList.add(new Weather(date, "Wed", 23, 10, "Scattered Thunderstorms"));
        date = dateFormat.parse("1 Oct 2015");
        forecastList.add(new Weather(date, "Thu", 23, 11, "Mostly Sunny"));
        Map<String,String> requestParams = new HashMap<String,String>();
        requestParams.put("Ankara", "Turkey");
        when(mockWeatherRetriever.getForeCast(requestParams)).thenReturn(forecastList);
                
        List<Weather> expectedList = new ArrayList<Weather>(forecastList);
         
        assertEquals(expectedList, requestHandler.getForecast(requestParams).getForecastList());
    }

}
