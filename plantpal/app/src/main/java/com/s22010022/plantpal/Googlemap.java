package com.s22010022.plantpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class Googlemap extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap map;
    List<Address> listGeoCoder;
    Button nextToDash;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googlemap);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapfrag);
        mapFragment.getMapAsync(this);

        nextToDash = findViewById(R.id.nextToDash);
        nextToDash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Googlemap.this,Dashbord.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        map = googleMap;
        LatLng kalawana = new LatLng(6.790086, 79.979816);
        map.addMarker(new MarkerOptions().position(kalawana).title("Kalawana Branch"));
        LatLng ratnapura = new LatLng(6.843449, 79.957960);
        map.addMarker(new MarkerOptions().position(ratnapura).title("Ratnapura Branch"));
        LatLng colombo = new LatLng(6.9271, 79.8612);
        map.addMarker(new MarkerOptions().position(colombo).title("Colombo Main Branch"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(kalawana, 10.0f));
        map.getUiSettings().setZoomControlsEnabled(true);
    }
}