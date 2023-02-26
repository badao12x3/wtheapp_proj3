package com.example.weatherapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity implements
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        GoogleMap.OnMapClickListener,
        OnMapReadyCallback {

    private static final String TAG = "MapActivity";
    private PlacesClient mPlacesClient;

    private static final int M_MAX_ENTRIES = 5;
    private String[] mLikelyPlaceNames;
    private String[] mLikelyPlaceAddresses;
    private String[] mLikelyPlaceAttributions;
    private LatLng[] mLikelyPlaceLatLngs;

    private GoogleMap mMap;
    MarkerOptions markerOptions, currentMarkerOptions;
    LatLng mylocationLatLng;
    Marker marker0, marker;
    Location locationByGps,locationByNetwork, currentLocation;
    Double longitude, latitude;
    Geocoder geocoder;

    String city = null;
    String country = null;

    TextView tvCityNameMap, tvDetailInfoCity;
    Button btnConfirmMap;

    //    private ActivityMapsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        tvCityNameMap = findViewById(R.id.tvCityNameMap);
        tvDetailInfoCity = findViewById(R.id.tvDetailInfoCity);
        btnConfirmMap = findViewById(R.id.btnConfirmMap);
        geocoder = new Geocoder(this, Locale.getDefault());

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapsFragment);
        supportMapFragment.getMapAsync(this);

        btnConfirmMap.setEnabled(false);
        btnConfirmMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("CityNameChosenByUser",city);
                intent.putExtra("CountryNameChosenByUser",country);
                intent.putExtra("LatChosenByUser",latitude);
                intent.putExtra("LongChosenByUser",longitude);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return true;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        markerOptions = new MarkerOptions();
        currentMarkerOptions = new MarkerOptions();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        LocationListener gpsLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                locationByGps= location;
            }
        };

        LocationListener networkLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                locationByNetwork= location;
            }
        };

        if (hasGps) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000,
                    0F,
                    gpsLocationListener
            );
        }
        if (hasNetwork) {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    5000,
                    0F,
                    networkLocationListener
            );
        }


        Location lastKnownLocationByGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(lastKnownLocationByGps != null) {
            locationByGps = lastKnownLocationByGps;
        }

        Location lastKnownLocationByNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(lastKnownLocationByNetwork != null) {
            locationByGps = lastKnownLocationByNetwork;
        }

        if (locationByGps != null && locationByNetwork != null) {
            if (locationByGps.getAccuracy() > locationByNetwork.getAccuracy()) {
                currentLocation = locationByGps;
                latitude = currentLocation.getLatitude();
                longitude = currentLocation.getLongitude();
            } else {
                currentLocation = locationByNetwork;
                latitude = currentLocation.getLatitude();
                longitude = currentLocation.getLongitude();
            }
        }else {
            if(locationByGps != null){
                currentLocation = locationByGps;
                latitude = currentLocation.getLatitude();
                longitude = currentLocation.getLongitude();
            }
            if(locationByNetwork != null){
                currentLocation = locationByNetwork;
                latitude = currentLocation.getLatitude();
                longitude = currentLocation.getLongitude();
            }
        }

//        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(currentLocation == null) {
            Toast.makeText(this, "Không lấy được vị trí hiện tại", Toast.LENGTH_SHORT).show();

        }else {
            mylocationLatLng = new LatLng(latitude, longitude);
            marker = mMap.addMarker(currentMarkerOptions.position(mylocationLatLng).title("Vị trí hiện tại của bạn!").snippet("Thiết bị đang ở đây!"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylocationLatLng,10));
        }



        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnMapClickListener(this);


//        LatLng sydney = new LatLng(-34,151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marking in Sydney!"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mylocationLatLng,17));
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        mylocationLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        marker0 = mMap.addMarker(currentMarkerOptions.position(mylocationLatLng).title("Vị trí hiện tại của bạn!").snippet("Thiết bị đang ở đây!"));
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        if (marker0 != null) {
            marker0.remove();
        }
        if (marker != null) {
            marker.remove();
        }
        marker = mMap.addMarker(markerOptions.position(latLng).title("Vị trí bạn đang chọn!"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));

        List<Address> address = null;
        Log.v("TAG",latLng.latitude +" lat");
        Log.v("TAG",latLng.longitude +" long");
        longitude = latLng.longitude;
        latitude = latLng.latitude;
        try {
            address = geocoder.getFromLocation(latLng.latitude,latLng.longitude, 1);
            if (address.get(0).getLocality() != null) city = address.get(0).getLocality();
            else city = address.get(0).getAdminArea();
            country = address.get(0).getCountryCode();

            if(city != null){
                tvCityNameMap.setText(city);
                btnConfirmMap.setEnabled(true);
            }else{
                tvCityNameMap.setText("Không xác định");
                btnConfirmMap.setEnabled(false);
            }
            tvDetailInfoCity.setText(country);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    private void getCurrentPlaceLikelihoods() {
//        // Use fields to define the data types to return.
//        List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS,
//                Place.Field.LAT_LNG);
//
//        // Get the likely places - that is, the businesses and other points of interest that
//        // are the best match for the device's current location.
//        @SuppressWarnings("MissingPermission")
//        final FindCurrentPlaceRequest request = FindCurrentPlaceRequest.builder(placeFields).build();
//        @SuppressLint("MissingPermission")
//        Task<FindCurrentPlaceResponse> placeResponse = mPlacesClient.findCurrentPlace(request);
//        placeResponse.addOnCompleteListener(this,
//                new OnCompleteListener<FindCurrentPlaceResponse>() {
//                    @Override
//                    public void onComplete(@NonNull Task<FindCurrentPlaceResponse> task) {
//                        if (task.isSuccessful()) {
//                            FindCurrentPlaceResponse response = task.getResult();
//                            // Set the count, handling cases where less than 5 entries are returned.
//                            int count;
//                            if (response.getPlaceLikelihoods().size() < M_MAX_ENTRIES) {
//                                count = response.getPlaceLikelihoods().size();
//                            } else {
//                                count = M_MAX_ENTRIES;
//                            }
//
//                            int i = 0;
//                            mLikelyPlaceNames = new String[count];
//                            mLikelyPlaceAddresses = new String[count];
//                            mLikelyPlaceAttributions = new String[count];
//                            mLikelyPlaceLatLngs = new LatLng[count];
//
//                            for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
//                                Place currPlace = placeLikelihood.getPlace();
//                                mLikelyPlaceNames[i] = currPlace.getName();
//                                mLikelyPlaceAddresses[i] = currPlace.getAddress();
//                                mLikelyPlaceAttributions[i] = (currPlace.getAttributions() == null) ?
//                                        null : TextUtils.join(" ", currPlace.getAttributions());
//                                mLikelyPlaceLatLngs[i] = currPlace.getLatLng();
//
//                                String currLatLng = (mLikelyPlaceLatLngs[i] == null) ?
//                                        "" : mLikelyPlaceLatLngs[i].toString();
//
//                                Log.i(TAG, String.format("Place " + currPlace.getName()
//                                        + " has likelihood: " + placeLikelihood.getLikelihood()
//                                        + " at " + currLatLng));
//
//                                i++;
//                                if (i > (count - 1)) {
//                                    break;
//                                }
//                            }
//
//
//                            // COMMENTED OUT UNTIL WE DEFINE THE METHOD
//                            // Populate the ListView
//                            // fillPlacesList();
//                        } else {
//                            Exception exception = task.getException();
//                            if (exception instanceof ApiException) {
//                                ApiException apiException = (ApiException) exception;
//                                Log.e(TAG, "Place not found: " + apiException.getStatusCode());
//                            }
//                        }
//                    }
//                });
//    }


}