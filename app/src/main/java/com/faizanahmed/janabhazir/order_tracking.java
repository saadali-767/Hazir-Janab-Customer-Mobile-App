package com.faizanahmed.janabhazir;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class order_tracking extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference mDatabase;
    int bookingID,vendorID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);
        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        bookingID=intent.getIntExtra("bookingId",0);
        NormalBooking normalBooking = userbookingsdataholder.findOrdersByProductId(bookingID);
        vendorID=normalBooking.getVendorId();



        // Initialize Firebase database reference
        mDatabase = FirebaseDatabase.getInstance().getReference("coordinates");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Log the vendorID to confirm what we're working with
        Log.d("FirebaseDebug", "Vendor ID: " + vendorID);

        // Prepare the reference to the Firebase database
        DatabaseReference vendorRef = mDatabase.child(String.valueOf(vendorID));
        Log.d("FirebaseDebug", "Firebase Database Reference: " + vendorRef);

        // Attempt to read data for the specified vendorID
        vendorRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Log the full snapshot to see what data we received
                Log.d("FirebaseDebug", "Full DataSnapshot: " + dataSnapshot);

                if (dataSnapshot.exists()) {
                    // Try to read the latitude and longitude values
                    Double latitude = dataSnapshot.child("latitude").getValue(Double.class);
                    Double longitude = dataSnapshot.child("longitude").getValue(Double.class);

                    // Log the latitude and longitude to see if they're what we expect
                    Log.d("FirebaseDebug", "Latitude: " + latitude + ", Longitude: " + longitude);

                    if (latitude != null && longitude != null) {
                        // If we have both coordinates, proceed to update the map
                        LatLng vendorLocation = new LatLng(latitude, longitude);
                        mMap.clear();
                        mMap.addMarker(new MarkerOptions().position(vendorLocation).title("Vendor Location"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(vendorLocation, 16.0f));
                    } else {
                        // If either coordinate is null, log an error
                        Log.e("FirebaseDebug", "One of the coordinates (latitude/longitude) is null for vendorID: " + vendorID);
                    }
                } else {
                    // If the snapshot does not exist, log an error
                    Log.e("FirebaseDebug", "Snapshot does not exist for vendorID: " + vendorID);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // If the database read is cancelled, log the error
                Log.e("FirebaseDebug", "Database read cancelled: " + databaseError.toException());
            }
        });
    }




}