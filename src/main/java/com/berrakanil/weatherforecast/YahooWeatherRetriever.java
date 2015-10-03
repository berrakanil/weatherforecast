package com.berrakanil.weatherforecast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class YahooWeatherRetriever implements IWeatherRetriever {
    private final String FORECASTITEM = "item";
    private final String CHARSET = java.nio.charset.StandardCharsets.UTF_8.name();
    private final String URL = "https://query.yahooapis.com/v1/public/yql";
    private final String DATATABLES = "store://datatables.org/alltableswithkeys";
    private final String FORMAT = "json";
    private final String YQL = "select %s from weather.forecast where woeid in (select woeid from geo.places(1) where text='%s,%s') and u='c'";
    private List<Weather> forecastList;

    protected String createUrlString(Map<String, String> requestParams, String item)
            throws UnsupportedEncodingException {
        String city = requestParams.get("city");
        String country = requestParams.get("country");
        String requestQuery = String.format(YQL, item, city, country);
        String query = String.format(URL + "?q=%s&format=%s&env=%s", URLEncoder.encode(requestQuery, CHARSET),
                URLEncoder.encode(FORMAT, CHARSET), URLEncoder.encode(DATATABLES, CHARSET));
        return query;
    }

    protected String retrieveResponse(String url) throws Exception {
    	StringBuilder jsonLine = new StringBuilder();
        try {
        	URLConnection connection = new URL(url).openConnection();
        	connection.setRequestProperty("Accept-Charset", CHARSET);
        	InputStream response = connection.getInputStream();
        	BufferedReader reader = new BufferedReader(new InputStreamReader(response, CHARSET));
        	try {
        		String line;
        		while((line = reader.readLine()) != null) {
                    jsonLine.append(line).append("\n");
                }
        	} finally {
                reader.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read from json stream", e);
        }
        return jsonLine.toString();
    }

    public List<Weather> parseForecastResponse(String line) throws JSONException, ParseException {
        JSONObject json = new JSONObject(line);
        JSONArray forecastResponseArray = json.getJSONObject("query").getJSONObject("results").getJSONObject("channel")
                .getJSONObject("item").getJSONArray("forecast");

        forecastList = new ArrayList<Weather>();
        for (int i = 0; i < forecastResponseArray.length(); ++i) {
            JSONObject forecastResponseItem = forecastResponseArray.getJSONObject(i);
            DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
            Date date = dateFormat.parse(forecastResponseItem.getString("date"));
            forecastList.add(new Weather(date, 
                                         forecastResponseItem.get("day").toString(), 
                                         forecastResponseItem.getInt("high"),
                                         forecastResponseItem.getInt("low"), 
                                         forecastResponseItem.get("text").toString()));
        }
        return forecastList;
    }

    protected String prepareResponse(Map<String, String> requestParams, String urlParam) throws Exception {
        String url = createUrlString(requestParams, urlParam);
        String line = retrieveResponse(url);
        return line;
    }

    public List<Weather> getForeCast(Map<String, String> requestParams) throws Exception {
        String line = prepareResponse(requestParams, FORECASTITEM);
        List<Weather> forecastList = parseForecastResponse(line);
        return forecastList;
    }

}
