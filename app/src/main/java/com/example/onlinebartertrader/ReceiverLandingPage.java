package com.example.onlinebartertrader;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReceiverLandingPage extends AppCompatActivity implements View.OnClickListener, LocationListener {

    //Firebase
    FirebaseDatabase database;
    DatabaseReference receiverDBRefAvailable;
    DatabaseReference receiverDBRefHistory;

    //view for the lists
    ListView receiverLists;
    ListView receiverTradeList;

    Button receiverTradedBtn;
    Button receiverAvailableBtn;

    //arraylists for listviews
    ArrayList<String> receiverItems = new ArrayList<>();

    //Location
    private LocationManager locationManager;
    private String provider;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 123;
    String userEmailAddress;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

        //registering the button for the receiver's landing page
        //onclick listener listens set for each button click
        receiverAvailableBtn = findViewById(R.id.availableProductsReceiver);
        receiverAvailableBtn.setOnClickListener(this);

        receiverTradedBtn = findViewById(R.id.tradedHistoryReceiver);
        receiverTradedBtn.setOnClickListener(this);

        //init database
        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");
        userEmailAddress = getIntent().getStringExtra("emailAddress");
        receiverDBRefAvailable = database.getReference("Users").child("Receiver").child(userEmailAddress);
        receiverDBRefHistory = database.getReference("Users").child("Receiver").child(userEmailAddress);
        initLocation();
    }

    @Override
    public void onClick(View view) {

        //array Adapter for the listview to list all the items of the provider.
        final ArrayAdapter<String> receiverArrAdapter = new ArrayAdapter<String>
                (ReceiverLandingPage.this, android.R.layout.simple_list_item_1, receiverItems);

        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");

        //if availableProduct button is clicked -> show available products in that area.
        if (view.getId() == R.id.availableProductsReceiver){
            //register the views, buttons and other components for the receiver landing page.
            receiverLists = (ListView) findViewById(R.id.receiverListReceiver);
            receiverLists.setAdapter(receiverArrAdapter);

            //creating reference variable inside the databased called "User"
            receiverDBRefAvailable = database.getReference("Users").child("Receiver").child(userEmailAddress);

            //Firebase data addition, modification, deletion, reading performed through this section.
            receiverDBRefAvailable.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    String valueRead = snapshot.getValue(String.class);
                    receiverItems.add(valueRead);
                    receiverArrAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    receiverArrAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        //if traded items button is clicked -> show traded items history of receiver
        if (view.getId() == R.id.tradedHistoryReceiver){
            //register the views, buttons and other components for the receiver landing page.
            receiverTradeList = (ListView) findViewById(R.id.receiverTraded);
            receiverTradeList.setAdapter(receiverArrAdapter);

            receiverDBRefHistory = database.getReference("Users").child("Receiver").child(userEmailAddress);

            //Firebase data addition, modification, deletion, reading performed through this section.
            receiverDBRefHistory.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    String keyRead = snapshot.getKey();
                    receiverItems.add(keyRead);
                    receiverArrAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    receiverArrAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void initLocation(){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = LocationManager.GPS_PROVIDER;

        // check if GPS is enabled
        if (!locationManager.isProviderEnabled(provider)) {
            // show an alert dialog to prompt the user to enable GPS
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }

        // request location updates
        locationManager.requestLocationUpdates(provider,0,0,this);
    }

    @Override
    public void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        TextView textView = findViewById(R.id.locationString);
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                String addressString = address.getAddressLine(0);
                textView.setText(addressString);
                receiverDBRefAvailable.child("location").setValue(addressString);
            }
        } catch (IOException e) {
            e.printStackTrace();
            textView.setText("CAN NOT FIND LOCATION");
        }
    }
}
