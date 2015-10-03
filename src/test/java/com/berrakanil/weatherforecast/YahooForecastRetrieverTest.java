package com.berrakanil.weatherforecast;

import static org.junit.Assert.*;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class YahooForecastRetrieverTest {
    
    private final String templateResponse = 
            "{\"query\":{\"count\":1,\"created\":\"2015-09-27T20:40:58Z\",\"lang\":\"en-US\",\"results\":"
        +    "  {\"channel\":"
        +    "    {\"item\":"
        +    "      {\"condition\": "
        +    "        {\"code\":\"31\",\"date\":\"Sun, 27 Sep 2015 10:59 pm EEST\",\"temp\":\"20\",\"text\":\"Clear\"},\"description\":\"\",\"forecast\":"
        +    "          ["
        +    "            {\"code\":\"33\",\"date\":\"27 Sep 2015\",\"day\":\"Sun\",\"high\":\"29\",\"low\":\"15\",\"text\":\"Mostly Clear\"},"
        +    "            {\"code\":\"30\",\"date\":\"28 Sep 2015\",\"day\":\"Mon\",\"high\":\"31\",\"low\":\"16\",\"text\":\"Partly Cloudy\"},"
        +    "            {\"code\":\"22\",\"date\":\"29 Sep 2015\",\"day\":\"Tue\",\"high\":\"-5\",\"low\":\"-9\",\"text\":\"Heavy Snow\"},"
        +    "          ]"
        +    "        }"
        +    "      }"
        +    "    }"
        +    "  }"
        +    "}";
    
    
    private final String templateResponseWithNewLine = 
            "{\"query\":{\"count\":1,\"created\":\"2015-09-27T20:40:58Z\",\"lang\":\"en-US\",\"results\":"
        +    "  {\"channel\":"
        +    "    {\"item\":"
        +    "      {\"condition\": "
        +    "        {\"code\":\"31\",\"date\":\"Sun, 27 Sep 2015 10:59 pm EEST\",\"temp\":\"20\",\"text\":\"Clear\"},\"description\":\"\",\"forecast\":\n"
        +    "          ["
        +    "            {\"code\":\"33\",\"date\":\"27 Sep 2015\",\"day\":\"Sun\",\"high\":\"29\",\"low\":\"15\",\"text\":\"Mostly Clear\"},"
        +    "            {\"code\":\"30\",\"date\":\"28 Sep 2015\",\"day\":\"Mon\",\"high\":\"31\",\"low\":\"16\",\"text\":\"Partly Cloudy\"},"
        +    "            {\"code\":\"22\",\"date\":\"29 Sep 2015\",\"day\":\"Tue\",\"high\":\"-5\",\"low\":\"-9\",\"text\":\"Heavy Snow\"},"
        +    "          ]"
        +    "        }"
        +    "      }"
        +    "    }"
        +    "  }"
        +    "}";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testParseForecastResponse_validLine() throws JSONException, ParseException {
        YahooWeatherRetriever forecastRetriever = new YahooWeatherRetriever();
        
        List<Weather> expectedList = new ArrayList<Weather>();
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        
        Date date = dateFormat.parse("27 Sep 2015");
        expectedList.add(new Weather(date, "Sun", 29, 15, "Mostly Clear"));
        
        date = dateFormat.parse("28 Sep 2015");
        expectedList.add(new Weather(date, "Mon", 31, 16, "Partly Cloudy"));
        
        date = dateFormat.parse("29 Sep 2015");
        expectedList.add(new Weather(date, "Tue", -5, -9, "Heavy Snow"));
        
        assertEquals(expectedList, forecastRetriever.parseForecastResponse(templateResponse));
    }
    
    
    @Test
    public void testParseForecastResponse_validLineWithNewLine() throws JSONException, ParseException {
        YahooWeatherRetriever forecastRetriever = new YahooWeatherRetriever();
        
        List<Weather> expectedList = new ArrayList<Weather>();
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        
        Date date = dateFormat.parse("27 Sep 2015");
        expectedList.add(new Weather(date, "Sun", 29, 15, "Mostly Clear"));
        
        date = dateFormat.parse("28 Sep 2015");
        expectedList.add(new Weather(date, "Mon", 31, 16, "Partly Cloudy"));
        
        date = dateFormat.parse("29 Sep 2015");
        expectedList.add(new Weather(date, "Tue", -5, -9, "Heavy Snow"));
        
        assertEquals(expectedList, forecastRetriever.parseForecastResponse(templateResponseWithNewLine));
    }
    
    @Test(expected=Exception.class)
    public void testParseForecastResponse_InvalidLine() throws JSONException, ParseException {
        YahooWeatherRetriever forecastRetriever = new YahooWeatherRetriever();
        List<Weather> responseList = forecastRetriever.parseForecastResponse("InvalidLine");
        assertTrue(false);
    }
}
