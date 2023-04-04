package com.example.onlinebartertrader;

import static androidx.test.InstrumentationRegistry.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExchangeHistoryActivity extends AppCompatActivity {

    ListView exchangeHistoryList;
    DatabaseReference exchangeHistoryRef;
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchangehistory);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");

        // Get the intent that started this activity
        Intent intent = getIntent();

        // Retrieve the user type from the intent extra
        String userType = intent.getStringExtra("userType");

        // Set the user type
        setUserType(userType);

        // Get a reference to the exchange history database node based on the user type
        setExchangeHistoryRef(userType, database);

        // Set up the exchange history list view
        exchangeHistoryList = findViewById(R.id.exchange_history_recycler_view);

        //Attach a value event listener to the exchange history reference
        exchangeHistoryRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange (@NonNull DataSnapshot dataSnapshot){
                        //Create a list to hold the formatted exchange history strings
                List<String> exchangeHistoryStrings = new ArrayList<>();

                try{
                //Iterate through the exchange history items and create formatted strings
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    String itemStatus = itemSnapshot.child("item").child("currentStatus").getValue(String.class);

                    //Check if the itemStatus is Sold
                    if (itemStatus != null && itemStatus.equals("Sold Out")) {
                        String productName = itemSnapshot.child("item").child("productName").getValue(String.class);
                        String transactionDate = itemSnapshot.child("item").child("transactionDate").getValue(String.class);
                        String cost = itemSnapshot.child("item").child("approxMarketValue").getValue(String.class);
                        String exchangeItem = itemSnapshot.child("item").child("preferredExchange").getValue(String.class);
                        String location = itemSnapshot.child("item").child("placeOfExchange").getValue(String.class);
                        String providerId = itemSnapshot.child("item").child("providerID").getValue(String.class);
                        String receiverId = itemSnapshot.child("item").child("receiverID").getValue(String.class);

                        String itemDetails = "Product Name: " + productName +
                                "\nTransaction Date: " + transactionDate +
                                "\nCost: " + cost +
                                "\nExchange Item: " + exchangeItem +
                                "\nLocation: " + location;

                        if (userType.equals("Provider")) {
                            itemDetails += "\nReceiver ID/Username: " + receiverId;
                        } else {
                            itemDetails += "\nProvider ID/Username: " + providerId;
                        }

                        exchangeHistoryStrings.add(itemDetails);
                    }
                }
                // Create an ArrayAdapter to display the formatted exchange history strings in the ListView
                ArrayAdapter<String> adapter = new ArrayAdapter<>(ExchangeHistoryActivity.this,
                        android.R.layout.simple_list_item_1, exchangeHistoryStrings);

                // Set the adapter for the ListView
                exchangeHistoryList.setAdapter(adapter);
            }
            catch (NullPointerException e) {
                // Handle the null exception by displaying an error message to the user
                Toast.makeText(getContext(), "Exchange history is null", Toast.LENGTH_SHORT).show();
            }
        }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the database error
            }
        });
    }


    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setExchangeHistoryRef(String userRule, FirebaseDatabase database) {
        if (userRule.equals("Provider")) {
            exchangeHistoryRef = database.getReference("User/Provider/items");
        } else {
            exchangeHistoryRef = database.getReference("User/Receiver/items");
        }
    }
}
