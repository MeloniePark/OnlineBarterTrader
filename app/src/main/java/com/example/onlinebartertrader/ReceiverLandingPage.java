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
import android.widget.ImageButton;
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

/**
 * The ReceiverLandingPage class is an activity that implements the View.OnClickListener and LocationListener interfaces.
 * It is used as a landing page for the receiver,
 * where they can view items posted by providers and select one to exchange with.
 * The LocationListener interface is used to listen for updates to the user's location,
 * which is used to calculate the distance between the user and the provider.
 * The class also includes methods for initializing the activity, handling button clicks, and updating the user's location.
 */
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
    ImageButton statsBtn;
    String userEmailAddress;

    private static final String STR_EMAIL_ADDR = "emailAddress";

    //arraylists for listviews
    ArrayList<String> receiverItems = new ArrayList<>();

    //Location
    private LocationManager locationManager;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 123;

    /**
     This activity represents the landing page of the receiver. It contains buttons to navigate to
     available products and search items, as well as a button to view statistics of past transactions.
     It also registers a listener for new items added to the database and sends an alert to the user
     if a new item is added that matches their preferences.
     Implements View.OnClickListener interface to listen to button clicks and LocationListener interface
     to listen to location changes.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

        userEmailAddress = getIntent().getStringExtra(STR_EMAIL_ADDR);
        //registering the button for the receiver's landing page
        //onclick listener listens set for each button click
        receiverAvailableBtn = findViewById(R.id.availableProductsReceiver);
        receiverAvailableBtn.setOnClickListener(this);

        receiverSearchBtn = findViewById(R.id.searchButtonReceiver);
        receiverSearchBtn.setOnClickListener(this);

        statsBtn = findViewById(R.id.imageButton);
        statsBtn.setOnClickListener(this);

        //init database
        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");
        userEmailAddress = getIntent().getStringExtra(STR_EMAIL_ADDR);

        if (userEmailAddress == null){
            userEmailAddress = "test@dalca";
        }
        receiverDBRefAvailable = database.getReference("Users/Receiver").child(userEmailAddress);
        receiverDBRefHistory = receiverDBRefAvailable;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // US6 new functionality: alert the receiver when there is a new item added
        // and is interested by the user
        Alert itemAlert = new Alert(userEmailAddress, ReceiverLandingPage.this);
        itemAlert.startListening();
        initLocation();

    }

    /**
     * The onClick method is called when a view is clicked. It handles the button clicks on the ReceiverLandingPage activity.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {

        //if availableProduct button is clicked -> show available products in that area.
        if (view.getId() == R.id.availableProductsReceiver){
            //register the views, buttons and other components for the receiver landing page.
            receiverLists = (ListView) findViewById(R.id.receiverListReceiver);

            ReceiverItemList myList = new ReceiverItemList(userEmailAddress, receiverLists, ReceiverLandingPage.this);
            myList.startListening();
        }

        //if search item button is clicked -> switch to search page
        if (view.getId() == R.id.searchButtonReceiver){
            Intent intent = new Intent(this, SearchActivity.class);
            intent.putExtra(STR_EMAIL_ADDR, userEmailAddress.toLowerCase());
            startActivity(intent);
        }

        if (view.getId() == R.id.imageButton){
            Intent intent = new Intent(this, UserInfo.class);
            intent.putExtra("emailAddress", userEmailAddress.toLowerCase());
            intent.putExtra("userLoggedIn", "Receiver");
            startActivity(intent);
        }

    }

    /**
     Initializes the location manager and sets up location updates for the receiver.
     If the GPS provider is not enabled, it prompts the user to enable it.
     If location permission is not granted, it requests it.
     */
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

    /**
     This method is called when the location of the receiver is changed. It updates the receiver's location in the Firebase
     database and displays the location in the UI by converting the latitude and longitude coordinates to a location string
     using a geocoder. The location is then displayed in a text view.

     @param location the updated location of the receiver
     */
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
