package com.example.hikerswatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                UpdateLocationInfo(location);
            }
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocation != null){
                UpdateLocationInfo(lastKnownLocation);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startListening();
            }

    }
    public void startListening() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        }
    }

    public void UpdateLocationInfo(Location location) {
        TextView latTextView = findViewById(R.id.LatTextView);
        TextView longTextView = findViewById(R.id.longTextView);
        TextView accTextView = findViewById(R.id.AccTextView);
        TextView altiTextView = findViewById(R.id.altiTextView);
        TextView addressTextView = findViewById(R.id.adressTextView);

        latTextView.setText("Latitude: " + Double.toString(location.getLatitude()));
        longTextView.setText("Longitude: " + Double.toString(location.getLongitude()));
        accTextView.setText("Accuracy: " + Double.toString(location.getAccuracy()));
        altiTextView.setText("Altitude: " + Double.toString(location.getAltitude()));

        String address = "Address not found :( ";

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.get(0).getAdminArea() != null) {
                if (addresses != null && addresses.size() > 0) {
                address = "Address:\n";

                    if (addresses.get(0).getThoroughfare() != null) {
                        address += addresses.get(0).getThoroughfare() + "\n";
                    }
                    if (addresses.get(0).getLocality() != null) {
                        address += addresses.get(0).getLocality() + " ";
                    }
                    if (addresses.get(0).getAdminArea() != null) {
                        address += addresses.get(0).getAdminArea() + " ";
                    }
                    if (addresses.get(0).getPostalCode() != null) {
                        address += addresses.get(0).getPostalCode();
                    }
                }
            }
        } catch (Exception e) {
          e.printStackTrace();
        }

        addressTextView.setText(address);
    }
}