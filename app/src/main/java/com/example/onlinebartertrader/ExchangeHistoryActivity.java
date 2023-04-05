package com.example.onlinebartertrader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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
    private String userEmailAddress;
    private Button backButton;
    private String errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchangehistory);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");

        // Retrieve the user type and the user Email Address from the intent extra
        userType = getIntent().getStringExtra("userType");
        userEmailAddress = getIntent().getStringExtra("emailAddress");

        // Set the user type
        setUserRoleAndId(userType,userEmailAddress);

        // Get a reference to the exchange history database node based on the user type
        setExchangeHistoryRef(userType,userEmailAddress,database);

        // Set up the exchange history list view
        exchangeHistoryList = findViewById(R.id.exchange_history_recycler_view);

        //Attach a value event listener to the exchange history reference
        exchangeHistoryRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange (@NonNull DataSnapshot dataSnapshot){
                        //Create a list to hold the formatted exchange history strings
                List<String> exchangeHistoryStrings = new ArrayList<>();

                //Iterate through the exchange history items and create formatted strings
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    String itemStatus = itemSnapshot.child("currentStatus").getValue(String.class);

                    //Check if the itemStatus is Sold
                    if (itemStatus != null && itemStatus.equals("Sold Out")) {
                        String productName = itemSnapshot.child("productName").getValue(String.class);
                        String transactionDate = itemSnapshot.child("transactionDate").getValue(String.class);
                        String cost = itemSnapshot.child("approxMarketValue").getValue(String.class);
                        String exchangeItem = itemSnapshot.child("preferredExchange").getValue(String.class);
                        String location = itemSnapshot.child("placeOfExchange").getValue(String.class);
                        String providerId = itemSnapshot.child("providerID").getValue(String.class);
                        String receiverId = itemSnapshot.child("receiverID").getValue(String.class);

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
                //Create an ArrayAdapter to display the exchange history in the ListView
                ArrayAdapter<String> adapter = new ArrayAdapter<>(ExchangeHistoryActivity.this,
                        android.R.layout.simple_list_item_1, exchangeHistoryStrings);

                //Set the adapter for the ListView
                    if (exchangeHistoryList != null) {
                        exchangeHistoryList.setAdapter(adapter);
                    } else {
                        Log.e("ExchangeHistoryActivity", "exchangeHistoryList is null");
                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the database error
            }
        });

        // Get a reference to the back button
        backButton = findViewById(R.id.backToStat);

        // Set a click listener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the switch2StatPage() method to return to the User status page
                switch2StatPage();
            }
        });
    }


    public void setUserRoleAndId(String userType,String userEmailAddress) {
        this.userType = userType;
        this.userEmailAddress = userEmailAddress;
    }

    public boolean exchangeHistoryListIsNotNull() {
        if (exchangeHistoryList == null) {
            errorMessage = "Exchange history list is null".trim();
            setStatusMessage(errorMessage);
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean exchangeHistoryRefIsNotNull() {
        if (exchangeHistoryRef == null) {
            errorMessage = "Exchange history reference is null".trim();
            setStatusMessage(errorMessage);
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean isExchangeHistoryDisplayed(String userRole, String userId, String productName, String dateOfPurchase, String cost, String exchangeItem, String location, String providerId) {
    return true;
    }


    protected void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.errorMessage);
        statusLabel.setText(message.trim());
    }

    public void setExchangeHistoryRef(String userType, String userEmailAddress, FirebaseDatabase database) {
        if (userType.equals("Provider")) {
            exchangeHistoryRef = database.getReference("Users").child("Provider").child(userEmailAddress).child("items");
        } else {
            exchangeHistoryRef = database.getReference("Users").child("Receiver").child(userEmailAddress).child("items");
        }
    }

    protected void switch2StatPage() {
        Intent intent = new Intent(this, StatsPageActivity.class);
        startActivity(intent);
    }
}
