package com.example.onlinebartertrader;

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

public class ProviderLandingPage extends AppCompatActivity implements View.OnClickListener, LocationListener {

    //firebase
    FirebaseDatabase database;
    DatabaseReference providerDBRef;
    DatabaseReference providerDBRefLoc;

    //view for the lists
    ListView providerItemLists;

    Button providerPostBtn;

    //arraylists for listview
    ArrayList<String> providerItems = new ArrayList<>();

    //Location
    private LocationManager locationManager;
    private String provider;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 123;
    String userEmailAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

        //array Adapter for the listview to list all the items of the provider.
        final ArrayAdapter<String> providerArrAdapter = new ArrayAdapter<String>
                (ProviderLandingPage.this, android.R.layout.simple_list_item_1, providerItems);

        //register the views, buttons and other components for the provider landing page.
        providerItemLists = (ListView) findViewById(R.id.providerListProvider);
        //setting array Adapter for the ListView providerItemLists.
        providerItemLists.setAdapter(providerArrAdapter);
        providerPostBtn = findViewById(R.id.providerPostProvider);

        //listens for click of the post button
        providerPostBtn.setOnClickListener(this);

        //Firebase Connection
        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");
        //get user email
        userEmailAddress = getIntent().getStringExtra("emailAddress");
        //creating reference variable inside the databased called "User"
        providerDBRef = database.getReference("Users").child("Provider").child(userEmailAddress).child("items");

        //Firebase data addition, modification, deletion, reading performed through this section.
        providerDBRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                try {
                    //String valueRead = snapshot.getValue(String.class);
                    //providerItems.add(valueRead);
                    //providerArrAdapter.notifyDataSetChanged();

                    String itemType = snapshot.child("productType").getValue(String.class);
                    String itemName = snapshot.child("productName").getValue(String.class);

                    providerItems.add("Item Name: " + itemName + ", Item Type: " + itemType);
                    providerArrAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    System.out.println("It can not convert to string");
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                providerArrAdapter.notifyDataSetChanged();
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

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        }

        initLocation();
    }

    @Override
    public void onClick(View view) {
        //where we move on to posting provider's goods page.
        //Functionality will be added in future iteration 
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

        providerDBRefLoc = database.getReference("Users").child("Provider").child(userEmailAddress);
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        //set geocoder to default
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        //find the field to add text
        TextView textView = findViewById(R.id.locationStringProvider);

        try {
            String city = "";
            //get the location string and push to text view and data base
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            System.out.println(addresses);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                city = address.getLocality();
                String addressString = address.getAddressLine(0);
                textView.setText(addressString);
                providerDBRefLoc.child("location").setValue(city);
            }
        } catch (IOException e) {
            e.printStackTrace();
            textView.setText("CAN NOT FIND LOCATION");
        }
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

}
