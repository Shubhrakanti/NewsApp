package com.example.giddu.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by giddu on 3/26/17.
 */

public final class QueryUtils {

    private QueryUtils() {
    }

    public static List<NewsItem> fetchNewsData(String requestUrl){
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.getStackTraceString(e);
        }

        Log.d("JSON RESPONSE", jsonResponse);

        List<NewsItem> result = extractFeatureFromJson(jsonResponse);

        return result;



    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.getStackTraceString(e);
        }
        return url;
    }





    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("QueryUtils", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.getStackTraceString(e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static List<NewsItem> extractFeatureFromJson(String RESPONSE_JSON) {

        if (TextUtils.isEmpty(RESPONSE_JSON)) {
            return null;
        }

        List<NewsItem> list = new ArrayList<>();



        try{


            JSONObject baseJsonResponse = new JSONObject(RESPONSE_JSON);
            JSONObject reults = baseJsonResponse.getJSONObject("response");
            JSONArray newsList = reults.getJSONArray("results");

            for (int i=0; i<newsList.length(); i++){
                JSONObject currentItem = newsList.getJSONObject(i);
                String title = currentItem.getString("webTitle");
                String date = currentItem.getString("webPublicationDate").substring(0,10);
                String link = currentItem.getString("webUrl");
                String section = currentItem.getString("sectionId");


                NewsItem newsItem = new NewsItem(title, link, date, section);
                list.add(newsItem);


            }

        }
        catch (Exception e){
            Log.getStackTraceString(e);
        }

        return list;

    }




}
