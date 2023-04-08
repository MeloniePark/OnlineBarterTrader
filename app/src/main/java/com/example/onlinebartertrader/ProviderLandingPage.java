package com.example.onlinebartertrader;

import android.content.Intent;
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
import android.widget.AdapterView;
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
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.logging.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 ProviderLandingPage is the landing page for the provider user after logging in.
 This class is responsible for displaying the list of items posted by the provider,
 and allows the provider to post new items, view the chat page and see their statistics.
 The class extends the AppCompatActivity and implements View.OnClickListener and LocationListener.
 */
public class ProviderLandingPage extends AppCompatActivity implements View.OnClickListener, LocationListener {

    //firebase
    FirebaseDatabase database;
    DatabaseReference providerDBRef;
    DatabaseReference receiverIdDBRef;
    DatabaseReference providerDBRefLoc;

    //view for the lists
    ListView providerItemLists;
    ListView receiverIdList;
    Button providerPostBtn;
    Button providerChatBtn;
    String userEmailAddress;
    ImageButton statBtn;
    //Logging
    Logger logger = Logger.getLogger(ProviderLandingPage.class.getName());

    //String Constants
    private static final String STR_RECEIVER_RATING = "receiverRating";
    private static final String STR_EMAIL_ADDR = "emailAddress";
    private static final String STR_RECEIVER_ID = "receiverID";
    private static final String STR_ITEMS = "items";
    private static final String STR_PROVIDER = "Provider";

    //arraylists for listview
    ArrayList<String> providerItems = new ArrayList<>();
    ArrayList<String> receiverId = new ArrayList<>();
    //Location
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 123;

    /**
     Called when the activity is starting. This method sets up the UI components and
     initializes the Firebase database and its references. It also listens for changes in
     the database and updates the list view accordingly.
     @param savedInstanceState a Bundle object containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

        //array Adapter for the listview to list all the items of the provider.
        final ArrayAdapter<String> providerArrAdapter = new ArrayAdapter<>
                (ProviderLandingPage.this, android.R.layout.simple_list_item_1, providerItems);
        final ArrayAdapter<String> receiverIdArrAdapter = new ArrayAdapter<>
                (ProviderLandingPage.this, android.R.layout.simple_list_item_1, receiverId);

        //register the views, buttons and other components for the provider landing page.
        providerItemLists = (ListView) findViewById(R.id.providerListProvider);
        //setting array Adapter for the ListView providerItemLists.
        providerItemLists.setAdapter(providerArrAdapter);

        //register the views, buttons and other components for the provider landing page.
        receiverIdList = (ListView) findViewById(R.id.receiverIdList);
        //setting array Adapter for the ListView receiverIdList.
        receiverIdList.setAdapter(receiverIdArrAdapter);

        providerPostBtn = findViewById(R.id.providerPostProvider);
        providerChatBtn = findViewById(R.id.chatProvider);
        statBtn = findViewById(R.id.imageButton2);

        //listens for click of the post button
        providerPostBtn.setOnClickListener(this);
        providerChatBtn.setOnClickListener(this);
        statBtn.setOnClickListener(this);

        //Firebase Connection
        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");
        //get user email
        userEmailAddress = getIntent().getStringExtra(STR_EMAIL_ADDR);
        //creating reference variable inside the databased called "User"
        if (userEmailAddress == null){
            userEmailAddress = "test@dalca";
        }
        providerDBRef = database.getReference("Users").child(STR_PROVIDER).child(userEmailAddress).child(STR_ITEMS);

        //Firebase data addition, modification, deletion, reading performed through this section.
        providerDBRef.addChildEventListener(new ChildEventListener() {
            /**
             Firebase data addition, modification, deletion, reading performed through this section.
             @param snapshot The DataSnapshot of the new child node added to the "items" reference
             @param previousChildName The name of the previous child node, or null if there was no previous child node
             */
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                try {
                    String itemType = snapshot.child("productType").getValue(String.class);
                    String itemName = snapshot.child("productName").getValue(String.class);
                    String status = snapshot.child("currentStatus").getValue(String.class);
                    String itemID = snapshot.getKey();

                    providerItems.add("Item ID: " + itemID + ", Item Name: " + itemName + ", Item Type: " + itemType + ", Status: " + status);
                    providerArrAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    logger.info("It can not convert to string");
                }

                //set item click listener
                providerItemLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                        String clickedItem = (String) adapterView.getItemAtPosition(index);
                        // NOTE: I changed the string to add the item id and other stuffs here as well.
                        String[] itemParts = clickedItem.split(", ");
                        String itemID = itemParts[0].substring(9);

                        DatabaseReference itemsRef = database.getReference("Users/Provider/"+userEmailAddress);
                        itemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    DataSnapshot itemsSnapshot = dataSnapshot.child(STR_ITEMS);
                                    for (DataSnapshot itemSnapshot : itemsSnapshot.getChildren()) {
                                        String currentItemID = itemSnapshot.getKey();
                                        assert currentItemID != null;
                                        if (currentItemID.equalsIgnoreCase(itemID)) {
                                            // The item was found in the database. can also get other item if nessesary

                                            String itemType = itemSnapshot.child("productType").getValue(String.class);
                                            String itemName = itemSnapshot.child("productName").getValue(String.class);
                                            String approxMarketValue = itemSnapshot.child("approxMarketValue").getValue(String.class);
                                            String currentStatus = itemSnapshot.child("currentStatus").getValue(String.class);
                                            String preferredExchange = itemSnapshot.child("preferredExchange").getValue(String.class);
                                            String productReceived = itemSnapshot.child("productReceived").getValue(String.class);
                                            String receiverEnteredPrice = itemSnapshot.child("receiverEnteredPrice").getValue(String.class);
                                            String description = itemSnapshot.child("description").getValue(String.class);
                                            String transactionDate = itemSnapshot.child("transactionDate").getValue(String.class);

                                            String receiverID = itemSnapshot.child(STR_RECEIVER_ID).getValue(String.class);
                                            String receiverRating = itemSnapshot.child(STR_RECEIVER_RATING).getValue(String.class);

                                            if (receiverRating == null){
                                                receiverRating = "0";
                                            }
                                            assert currentStatus != null;
                                            if (currentStatus.equalsIgnoreCase("Sold Out")){
                                                Intent myIntent = new Intent(view.getContext(), SoldItemActivity.class);
                                                myIntent.putExtra("itemID", itemID);
                                                myIntent.putExtra("itemName", itemName);
                                                myIntent.putExtra("itemType", itemType);
                                                myIntent.putExtra("description", description);
                                                myIntent.putExtra("approxMarketValue", approxMarketValue);
                                                myIntent.putExtra("receiverEnteredPrice", receiverEnteredPrice);
                                                myIntent.putExtra("preferredExchange", preferredExchange);
                                                myIntent.putExtra("productReceived", productReceived);
                                                myIntent.putExtra("transactionDate", transactionDate);
                                                myIntent.putExtra(STR_RECEIVER_ID, receiverID);
                                                myIntent.putExtra("providerEmail", userEmailAddress);

                                                myIntent.putExtra(STR_RECEIVER_RATING,receiverRating);
                                                view.getContext().startActivity(myIntent);
                                            }
                                        }
                                    }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Unused in this iteration, but left for future implementation.
                            }
                        });
                    }
                });




            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                providerArrAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                //unused for now but possible to be used in future iteration
                throw new UnsupportedOperationException();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //unused for now but possible to be used in future iteration
                throw new UnsupportedOperationException();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //unused for now but possible to be used in future iteration
                throw new UnsupportedOperationException();
            }
        });

        receiverIdDBRef = database.getReference("Users").child(STR_PROVIDER).child(userEmailAddress).child(STR_ITEMS);
        receiverIdDBRef.addChildEventListener(new ChildEventListener() {
            /**
             This method is called when a child is added to the Firebase database under the "Provider" category.
             @param snapshot DataSnapshot object that contains data of the added child.
             @param previousChildName String object that represents the name of the previous child.
             */
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String receiverEmail = snapshot.child(STR_RECEIVER_ID).getValue(String.class);
                String receiverRating = snapshot.child(STR_RECEIVER_RATING).getValue(String.class);
                String itemKey = snapshot.getKey();
                if (receiverRating == null){
                    receiverRating = "0";
                }
                if (receiverEmail != null){

                    receiverId.add("Receiver ID: "+receiverEmail + "\nItem Key: "+itemKey+"\nReceiver Rating: "+receiverRating);
                }

                receiverIdArrAdapter.notifyDataSetChanged();
            }

            /**
             This method is called when a child is changed in the Firebase database under the "Provider" category.
             @param snapshot DataSnapshot object that contains data of the added child.
             @param previousChildName String object that represents the name of the previous child.
             */
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String itemKey = snapshot.getKey();
                // Find the item in the list using its key
                int position = -1;
                for (int i = 0; i < receiverId.size(); i++) {
                    String receiverString = receiverId.get(i);
                    Pattern pattern = Pattern.compile("Email: (.+)\nItem Key: ([-\\d.]+)\nReceiver Rating: ([-\\d.]+)", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(receiverString);
                    if (matcher.find()) {
                        String key = matcher.group(2);
                        if (itemKey.equals(key)) {
                            position = i;
                            break;
                        }
                    }
                }

                if (position != -1) {
                    // Update the item in the list
                    String receiverEmail = snapshot.child(STR_RECEIVER_ID).getValue(String.class);
                    String receiverRating = snapshot.child(STR_RECEIVER_RATING).getValue(String.class);

                    String updatedReceiverString = "Receiver ID: "+receiverEmail + "\nItem Key: "+itemKey+"\nReceiver Rating: "+receiverRating;

                    receiverId.set(position, updatedReceiverString);
                    receiverIdArrAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                //unused for now but possible to be used in future iteration
                throw new UnsupportedOperationException();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //unused for now but possible to be used in future iteration
                throw new UnsupportedOperationException();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //unused for now but possible to be used in future iteration
                throw new UnsupportedOperationException();
            }
        });

        receiverIdList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            /**
             Handles the item click events on the ListView of the Receiver IDs associated with each item the Provider has posted.
             Retrieves the receiver's email address, item key, and rating from the clicked item's string representation,
             and launches the ReceiverRating activity with these details as extras in the Intent.
             If the receiverRating string is null, it sets the default rating to 0.
             @param parent The AdapterView where the click happened.
             @param view The view within the AdapterView that was clicked.
             @param position The position of the view in the adapter.
             @param id The row id of the item that was clicked.
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String receiverString = parent.getItemAtPosition(position).toString();
                String email = "";
                String itemKey = "";
                String receiverRating = "";
                Pattern pattern = Pattern.compile("Email: (.+)\nItem Key: ([-\\d.]+)\nReceiver Rating: ([-\\d.]+)", Pattern.DOTALL);
                Matcher matcher = pattern.matcher(receiverString);
                if (matcher.find()) {
                    email = matcher.group(1);
                    itemKey = matcher.group(2);
                    receiverRating = matcher.group(3);
                }

                Intent intent = new Intent(ProviderLandingPage.this, ReceiverRating.class);

                if (receiverRating == null){
                    receiverRating = "0";
                }
                intent.putExtra("receiverEmail",email);
                intent.putExtra(STR_RECEIVER_RATING,receiverRating);
                intent.putExtra("userEmailAddress",userEmailAddress.toLowerCase());
                intent.putExtra("itemKey",itemKey);

                startActivity(intent);
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        }


        initLocation();
    }

    /**
     Handles clicks on the provider landing page buttons.
     @param view The button view that was clicked.
     */
    @Override
    public void onClick(View view) {
        //where we move on to posting provider's goods page.
        //Functionality will be added in future iteration
        if (view.getId() == R.id.providerPostProvider) {
            Intent intent = new Intent(this, ProviderPostItemActivity.class);
            intent.putExtra(STR_EMAIL_ADDR, userEmailAddress);
            startActivity(intent);
        }
        else if (view.getId() == R.id.chatProvider) {
            Intent intent = new Intent(this, ProviderChatActivity.class);
            intent.putExtra("providerEmail", userEmailAddress);
            startActivity(intent);
        }
        else if (view.getId() == R.id.imageButton2){
            Intent intent = new Intent(this, UserStats.class);
            intent.putExtra(STR_EMAIL_ADDR, userEmailAddress.toLowerCase());
            intent.putExtra("userLoggedIn", STR_PROVIDER);
            startActivity(intent);
        }

    }

    /**
     Initializes the location manager and sets up location updates for the provider.
     If the GPS provider is not enabled, it prompts the user to enable it.
     If location permission is not granted, it requests it.
     */
    private void initLocation(){
        String provider;
        LocationManager locationManager;
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

    /**
     This method is called when the location of the provider is changed. It updates the provider's location in the Firebase
     database and displays the location in the UI by converting the latitude and longitude coordinates to a location string
     using a geocoder. The location is then displayed in a text view.

     @param location the updated location of the receiver
     */
    @Override
    public void onLocationChanged(Location location) {

        providerDBRefLoc = database.getReference("Users").child(STR_PROVIDER).child(userEmailAddress);
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
            if (addresses != null && !addresses.isEmpty()) {
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
    public void onProviderEnabled(String provider) {
        //unused for now but possible to be used in future iteration
        throw new UnsupportedOperationException();
    }

    @Override
    public void onProviderDisabled(String provider) {
        //unused for now but possible to be used in future iteration
        throw new UnsupportedOperationException();
    }

}
