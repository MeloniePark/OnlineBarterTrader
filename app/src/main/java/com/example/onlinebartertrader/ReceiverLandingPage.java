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
    DatabaseReference receiverDBRefLoc;

    //view for the lists
    ListView receiverLists;
    ListView receiverTradeList;

    Button receiverTradedBtn;
    Button receiverAvailableBtn;
    Button receiverSearchBtn;
    String userEmailAddress;

    //arraylists for listviews
    ArrayList<String> receiverItems = new ArrayList<>();

    //Location
    private LocationManager locationManager;
    private String receiver;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 123;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

        userEmailAddress = getIntent().getStringExtra("emailAddress");
        //registering the button for the receiver's landing page
        //onclick listener listens set for each button click
        receiverAvailableBtn = findViewById(R.id.availableProductsReceiver);
        receiverAvailableBtn.setOnClickListener(this);

        receiverTradedBtn = findViewById(R.id.tradedHistoryReceiver);
        receiverTradedBtn.setOnClickListener(this);

        receiverSearchBtn = findViewById(R.id.searchButtonReceiver);
        receiverSearchBtn.setOnClickListener(this);
        //init database
        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");
        userEmailAddress = getIntent().getStringExtra("emailAddress");

        if (userEmailAddress == null){
            userEmailAddress = "test@dalca";
        }
        receiverDBRefAvailable = database.getReference("Users/Receiver").child(userEmailAddress);
        receiverDBRefHistory = receiverDBRefAvailable;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

//        initLocation();
        // US6 new functionality: alert the receiver when there is a new item added
        // and is interested by the user
        Alert itemAlert = new Alert(userEmailAddress, ReceiverLandingPage.this);
        itemAlert.startListening();
//        Alert itemAlert = new Alert(userEmailAddress, ReceiverLandingPage.this);
//        itemAlert.startListening();
        initLocation();

    }

    @Override
    public void onClick(View view) {

        //if availableProduct button is clicked -> show available products in that area.
        if (view.getId() == R.id.availableProductsReceiver){
            //register the views, buttons and other components for the receiver landing page.
            receiverLists = (ListView) findViewById(R.id.receiverListReceiver);
            System.out.println("receiverLists is clicked");

            ReceiverItemList myList = new ReceiverItemList(userEmailAddress, receiverLists, ReceiverLandingPage.this);
            myList.startListening();
        }

        //if traded items button is clicked -> show traded items history of receiver
        if (view.getId() == R.id.tradedHistoryReceiver){

            //array Adapter for the listview to list all the items of the provider.
            final ArrayAdapter<String> receiverArrAdapter = new ArrayAdapter<String>
                    (ReceiverLandingPage.this, android.R.layout.simple_list_item_1, receiverItems);

            database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");

            //register the views, buttons and other components for the receiver landing page.
            receiverTradeList = (ListView) findViewById(R.id.receiverTraded);
            receiverTradeList.setAdapter(receiverArrAdapter);

            receiverDBRefHistory = database.getReference("templateUser").child("receiver").child("receivedItem");

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

        //if search item button is clicked -> switch to search page
        if (view.getId() == R.id.searchButtonReceiver){
            Intent intent = new Intent(this, SearchActivity.class);
            intent.putExtra("emailAddress", userEmailAddress.toLowerCase());
            startActivity(intent);
        }
    }

    private void initLocation(){

        //Location
        String receiver = LocationManager.GPS_PROVIDER;

        // check if GPS is enabled
        if (!locationManager.isProviderEnabled(receiver)) {
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
        locationManager.requestLocationUpdates(receiver,0,0,this);

    }

    @Override
    public void onLocationChanged(Location location) {
        receiverDBRefLoc = database.getReference("Users").child("Receiver").child(userEmailAddress);
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        //set geocoder to default
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        //find the field to add text
        TextView textView = findViewById(R.id.locationStringReceiver);

        try {
            String city = "";
            //get the location string and push to text view and data base
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                city = address.getLocality();
                String addressString = address.getAddressLine(0);
                textView.setText(addressString);

                receiverDBRefLoc.child("location").setValue(city);
            }
        } catch (IOException e) {
            e.printStackTrace();
            textView.setText("CAN NOT FIND LOCATION");
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        //don't need this method
    }

    @Override
    public void onProviderEnabled(String provider) {
        //don't need this method
    }

    @Override
    public void onProviderDisabled(String provider) {
        //don't need this method
    }


}
