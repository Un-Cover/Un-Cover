package com.CS499.btsmith.uncover;

import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.CS499.btsmith.uncover.Data.Entry;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;

import java.util.List;
import java.util.Random;

public class Map extends FragmentActivity {

    private TextView latitudeField;
    private TextView longitudeField;
    private LocationManager locationManager;
    private String provider;
    private GoogleMap mMap;
    private Location myLoc;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setUpMapIfNeeded();

        Button pingButton = (Button) findViewById(R.id.pingButton);
        pingButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(Map.this, "Ping Ping Ping~", Toast.LENGTH_SHORT).show();
            }
        });

        Button newEntry = (Button) findViewById(R.id.newEntry);

        Button mapSearchSettings = (Button) findViewById(R.id.mapSearchSettings);
        mapSearchSettings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Map.this, searchSettings.class);
                startActivity(intent);
            }
        });

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);
        myLoc = location;
        latitudeField = (TextView) findViewById(R.id.TextView02);
        longitudeField = (TextView) findViewById(R.id.TextView04);
        moveCamera(location);

        // Initialize the location fields
        if (location != null)
        {
            System.out.println("Provider " + provider + " has been selected.");
            latitudeField.setText(Double.toString(location.getLatitude()));
            longitudeField.setText(Double.toString(location.getLongitude()));
        }
        else
        {
            latitudeField.setText("Location not available");
            longitudeField.setText("Location not available");
        }

        Button toListView = (Button) findViewById(R.id.mapList);
        toListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Map.this, neocacheListView.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("Location", myLoc);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        newEntry.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Map.this, EntryPage.class);
                Bundle bundle = new Bundle();
                bundle.putDouble("Latitude", myLoc.getLatitude());
                bundle.putDouble("Longitude", myLoc.getLongitude());
                bundle.putParcelable("Location", myLoc);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //Place all markers on map
        ParseQuery<Entry> query = ParseQuery.getQuery(Entry.class);
        query.findInBackground(new FindCallback<Entry>() {
            @Override
            public void done(List<Entry> list, ParseException e) {
                for(Entry entry : list)
                {
                    if(entry.getPosition() == null)
                    {
                        System.out.println("Bad post");
                    }
                    else
                    {
                        LatLng entryLocation = new LatLng(entry.getPosition().getLatitude(), entry.getPosition().getLongitude());
                        Marker mapMarker = mMap.addMarker(new MarkerOptions().position(entryLocation)
                                .title(entry.getDescription())
                                .snippet(entry.getName())
                                .icon(BitmapDescriptorFactory.defaultMarker(hueGenerator())));
                    }
                }
            }
        });
    }

    LocationListener locListen = new LocationListener()
    {
        @Override
        public void onLocationChanged(Location location)
        {
            Toast.makeText(Map.this, "LOCATION UPDATED", Toast.LENGTH_SHORT).show();
            myLoc = location;
            moveCamera(location);
            updateLocation(location);
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
            mMap.animateCamera(cameraUpdate);
            locationManager.removeUpdates(this);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public float hueGenerator()
    {
        float color;
        Random floatGen = new Random();
        color = floatGen.nextFloat() * 360;

        return color;
    }

    private void updateLocation(Location location)
    {
        myLoc = location;
        latitudeField.setText(Double.toString(location.getLatitude()));
        longitudeField.setText(Double.toString(location.getLongitude()));
    }

    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();
        long thing1 = 400;
        long thing2 = 1;
        locationManager.requestLocationUpdates(provider, thing1, 1.5f, locListen);
    }


    public void returnToDash(View view)
    {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        }
    }

    private void moveCamera(Location location){
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
        mMap.animateCamera(cameraUpdate);
    }
}
