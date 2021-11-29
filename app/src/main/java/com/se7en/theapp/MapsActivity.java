package com.se7en.theapp;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.se7en.theapp.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private boolean mLocationPermissionGranted = false;
    private static final float DEFAULT_ZOOM = 15f;

    private static final String TAG = "TAG";
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    //variables
    EditText mSearch;
    ImageView mGPS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getLocationPermission();
        mSearch = findViewById(R.id.searchText);
        mGPS = findViewById(R.id.mGPS);
        getLocationNSearch();
    }

    private void getLocationNSearch() {
        mGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                geoLocate();
            }
        });
    }
    //Fl 32819

    //geoLocating user on search
    private void geoLocate() {
        Log.d(TAG, "geoLocate: geolacating");

        String searchString = mSearch.getText().toString();
        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> addressList = new ArrayList<>();
        try {
            addressList = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){
            Log.e(TAG, "geoLocate: IOException: "+e.getMessage());
            Toast.makeText(MapsActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }
        if (addressList.size() > 0){
            Address address = addressList.get(0);
            Log.d(TAG, "geoLocate: found a location: "+ address.toString());
            //Move camera to location found
            moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),
                    DEFAULT_ZOOM, address.getAddressLine(0));
        }
        hideKeyboard();
    }

    private void createMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void getLocation() {
        mFusedLocationProviderClient = LocationServices
                .getFusedLocationProviderClient(this);
        try {
            if (mLocationPermissionGranted) {
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM, "My Location");

                        } else {
                            Toast.makeText(MapsActivity.this, "Cannot find location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "moveCamera: moving camera to: lat: " + latLng.latitude
                + " lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        if (!title.equals("My Location")) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(markerOptions);
        }
        hideKeyboard();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        if (mLocationPermissionGranted) {
            getLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Toast.makeText(MapsActivity.this, "getting location", Toast.LENGTH_SHORT).show();
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        LatLng newLoc = new LatLng(latLng.latitude, latLng.longitude);
        mMap.addMarker(new MarkerOptions().position(newLoc).title("new location added"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(newLoc));
    }

    private void getLocationPermission(){
        String[] permissions = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
        };
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            mLocationPermissionGranted = true;
            createMap();
        }else {
            ActivityCompat.requestPermissions(MapsActivity.this, permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLocationPermissionGranted = false;
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE){
            if (grantResults.length > 0){
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        mLocationPermissionGranted = true;
                        return;
                    }
                }
                mLocationPermissionGranted = true;

            }
        }
    }

    //to hide keyboard after enter
    private void hideKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager
                .LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}