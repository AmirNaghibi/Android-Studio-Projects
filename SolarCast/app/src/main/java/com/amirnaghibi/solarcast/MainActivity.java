package com.amirnaghibi.solarcast;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static TextView temperatureText;
    static TextView locationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temperatureText = (TextView) findViewById(R.id.locTemp);
        locationText = (TextView) findViewById(R.id.locName);

//        String name = "Vancouver";
//        locationText.setText(name);

//        String test = "VANCOUVER";
//        int testTemp =20;
//        locationText.setText(test);
//        temperatureText.setText(String.valueOf(testTemp));

        DownloadTask task = new DownloadTask();

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // provider is the location provider of the phone
        String provider = locationManager.getBestProvider(new Criteria(), false);

        // Get the last known location of the user
        // this is for when user does not grant permission.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);

        // get lattitude and longitude
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        // this URL only works for one lattitue and longitude. We want to get user's location. Using android locations API
        task.execute("http://samples.openweathermap.org/data/2.5/weather?lat="+ String.valueOf(lat) +"&lon="+ String.valueOf(lng)+"&appid=aefe5a5fcd610b3638e7eb570809d35d");
    }

}
