package com.example.onlinebartertrader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExchangeHistoryActivity extends AppCompatActivity {

    private ListView exchangeHistoryList;
    private DatabaseReference exchangeHistoryRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchangehistory);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");

        // Get the intent that started this activity
        Intent intent = getIntent();

        // Retrieve the user type from the intent extra
        String userType = intent.getStringExtra("userType");

        // Get a reference to the exchange history database node based on the user type
        if (userType.equals("Provider")) {
            exchangeHistoryRef = database.getInstance().getReference("User/Provider");
        } else {
            exchangeHistoryRef = database.getInstance().getReference("User/Receiver");
        }

        //items?

        // Set up the exchange history list view
        exchangeHistoryList = findViewById(R.id.exchange_history_recycler_view);

        // Attach a value event listener to the exchange history reference
        exchangeHistoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Create an array list to hold the exchange history items
                ArrayList<String> exchangeHistoryItems = new ArrayList<>();

                // Iterate through the exchange history items and add their details to the array list
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    String productName = itemSnapshot.child("productName").getValue(String.class);
                    String purchaseDate = itemSnapshot.child("dateOfAvailability").getValue(String.class);
                    String cost = itemSnapshot.child("approxMarketValue").getValue(String.class);
                    String exchangeItem = itemSnapshot.child("preferredExchange").getValue(String.class);
                    String location = itemSnapshot.child("placeOfExchange").getValue(String.class);

                    String itemDetails = "Product Name: " + productName +
                            "\nPurchase Date: " + purchaseDate +
                            "\nCost: " + cost +
                            "\nExchange Item: " + exchangeItem +
                            "\nLocation: " + location;

                    exchangeHistoryItems.add(itemDetails);
                }

                // Set up the exchange history list view adapter
                ArrayAdapter<String> exchangeHistoryAdapter = new ArrayAdapter<>(ExchangeHistoryActivity.this, android.R.layout.simple_list_item_1, exchangeHistoryItems);
                exchangeHistoryList.setAdapter(exchangeHistoryAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the database error
            }
        });
    }

    public void setUserRoleAndId(String userRole, String userId) {
    }

    public boolean isExchangeHistoryDisplayed(String userRole, String userId, String productName, String dateOfPurchase, String cost, String exchangeItem, String location, String providerId) {
        return false;
    }
}




