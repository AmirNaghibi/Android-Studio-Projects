package com.amirnaghibi.solarcast;


import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// AsyncTask<doInBackground return type, onProgressUpdate return type, onPostExecute return type>
public class DownloadTask extends AsyncTask<String,Void,String>{

    @Override
    protected String doInBackground(String... urls) {
        // This is where JSON data is stored when you download it.
        String result = "";
        URL url;
        HttpURLConnection urlConnection = null;


        try {
            url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();

            // get an Input stream from HttpURLConnection
            InputStream in = urlConnection.getInputStream();
            // you need input stream reader to read the input stream
            InputStreamReader reader = new InputStreamReader(in);

            // the reader outputs an integer
            int data = reader.read();

            // when data is finished reading, it turns to -1. Use a while loop while its not -1
            while(data != -1){
                char current = (char) data;

                result += current;

                data = reader.read();
            }

            return result;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    // change the parameter to result b/c thats the string we use.
    /* result is a string that looks like:

    {"coord":{"lon":139.01,"lat":35.02},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01n"}],
    "base":"stations","main":{"temp":285.514,"pressure":1013.75,"humidity":100,"temp_min":285.514,"temp_max":285.514,
    "sea_level":1023.22,"grnd_level":1013.75},"wind":{"speed":5.52,"deg":311},"clouds":{"all":0},"dt":1485792967,"sys"
    :{"message":0.0025,"country":"JP","sunrise":1485726240,"sunset":1485763863},"id":1907296,"name":"Tawarano","cod":200}
    *
    * */

    // Use a general Exception instead of a specific one

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        // make a new JSON object from the result
        try {
            JSONObject jsonObject = new JSONObject(result);
            // make a new json object that has a more specific type of data like temp, humidity , ...
            JSONObject weatherData = new JSONObject(jsonObject.getString("main"));

            // get the temperature from main, and parse it to a double
            double temperature = Double.parseDouble(weatherData.getString("temp"));

            int temperatureIntegerCelsius = (int)(temperature*1.8-459.67);

            String placeName = jsonObject.getString("name");

//            String test = "VANCOUVER";
//            int testTemp =20;
//            MainActivity.locationText.setText(test);
//            MainActivity.temperatureText.setText(String.valueOf(testTemp));

            String test = "VANCOUVER";
            int testTemp =20;
            MainActivity.locationText.setText(test);
            MainActivity.temperatureText.setText(String.valueOf(testTemp));

//            String name = "Canada";
//            locationText.setText(name);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
