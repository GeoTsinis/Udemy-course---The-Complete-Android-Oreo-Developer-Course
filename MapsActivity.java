package com.example.findusermap;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if( requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                mMap.clear();
                LatLng user = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(user).title("Users Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user,10));

                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                try {
                   List<Address> listAddresses =  geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

                   if ( listAddresses != null && listAddresses.size() > 0) {
                       String address = "";


                       if (listAddresses.get(0).getThoroughfare() != null){
                           address += listAddresses.get(0).getThoroughfare() + " ";
                       }
                       if (listAddresses.get(0).getLocality() != null){
                           address += listAddresses.get(0).getLocality() + " ";
                       }
                       if (listAddresses.get(0).getAdminArea() != null){
                           address += listAddresses.get(0).getAdminArea() + " ";
                       }
                       if (listAddresses.get(0).getPostalCode() != null){
                           address += listAddresses.get(0).getPostalCode();
                       }
                       Log.i("Address Info", address);
                       Toast.makeText(MapsActivity.this,address, Toast.LENGTH_SHORT).show();
                   }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
        };

        if (Build.VERSION.SDK_INT < 23) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 2, locationListener);
        } else {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);

            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 2, locationListener);
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                mMap.clear();
                LatLng user = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                mMap.addMarker(new MarkerOptions().position(user).title("User"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(user));
            }
        }


    }
}