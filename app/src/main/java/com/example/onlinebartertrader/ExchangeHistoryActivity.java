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

    private String itemStatus;
    private String productName;
    private String transactionDate;
    private String cost;
    private String exchangeItem;
    private String location;
    private String providerId;
    private String receiverId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchangehistory);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");

        // Retrieve the user type and the user Email Address from the intent extra
        userType = getIntent().getStringExtra("userType");
        userEmailAddress = getIntent().getStringExtra("emailAddress");

        //Set up the exchange history list view
        exchangeHistoryList = findViewById(R.id.exchange_history_recycler_view);

        //Set the user type and id
        setUserRoleAndId(userType,userEmailAddress);

        //Get a reference to the exchange history database node based on the user type
        setExchangeHistoryRef(userType,userEmailAddress,database);

        //Get a reference to the back button
        backButton = findViewById(R.id.backToStat);

        //Set a click listener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call the switch2StatPage() method to return to the User status page
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
        //Construct a string representing the exchange history item with the parameters
        String itemDetails = "Product Name: " + productName +
                "\nTransaction Date: " + dateOfPurchase +
                "\nCost: " + cost +
                "\nExchange Item: " + exchangeItem +
                "\nLocation: " + location;

        if (userRole.equals("Provider")) {
            itemDetails += "\nReceiver ID/Username: " + userId;
        } else {
            itemDetails += "\nProvider ID/Username: " + providerId;
        }

        //Check if the exchange history list view contains the string representation of the exchange history item
        if (exchangeHistoryListIsNotNull()) {
            ArrayAdapter<String> adapter = (ArrayAdapter<String>) exchangeHistoryList.getAdapter();
            for (int i = 0; i < adapter.getCount(); i++) {
                String exchangeHistoryString = adapter.getItem(i);
                if (exchangeHistoryString != null && exchangeHistoryString.equals(itemDetails)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.errorMessage);
        statusLabel.setText(message.trim());
    }

    public void setExchangeHistoryRef(String userType, String userEmailAddress, FirebaseDatabase database) {
        //Check if the user is a Provider or Receiver and fetch the respective data
        if (userType.equals("Provider")) {
            exchangeHistoryRef = database.getReference("Users").child("Provider").child(userEmailAddress).child("items");
            exchangeHistoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    fetchProviderData(dataSnapshot);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    //Handle the database error
                }
            });
        } else {
            DatabaseReference providerItemsRef = database.getReference("Users").child("Provider");
            providerItemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot providerSnapshot : dataSnapshot.getChildren()) {
                        DataSnapshot itemsSnapshot = providerSnapshot.child("items");
                        fetchReceiverData(itemsSnapshot);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    //Handle the database error
                }
            });
        }
    }

    private void fetchProviderData(DataSnapshot dataSnapshot) {
        List<String> exchangeHistoryStrings = new ArrayList<>();

        for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
            itemStatus = itemSnapshot.child("currentStatus").getValue(String.class);

            if (itemStatus != null && itemStatus.equals("Sold Out")) {
                productName = itemSnapshot.child("productName").getValue(String.class);
                transactionDate = itemSnapshot.child("transactionDate").getValue(String.class);
                cost = itemSnapshot.child("approxMarketValue").getValue(String.class);
                exchangeItem = itemSnapshot.child("preferredExchange").getValue(String.class);
                location = itemSnapshot.child("placeOfExchange").getValue(String.class);
                receiverId = itemSnapshot.child("receiverID").getValue(String.class);

                String itemDetails = "Product Name: " + productName +
                        "\nTransaction Date: " + transactionDate +
                        "\nCost: " + cost +
                        "\nExchange Item: " + exchangeItem +
                        "\nLocation: " + location +
                        "\nReceiver ID/Username: " + receiverId;

                exchangeHistoryStrings.add(itemDetails);
            }
        }
        updateAdapterWithData(exchangeHistoryStrings);
    }

    private void fetchReceiverData(DataSnapshot dataSnapshot) {
        List<String> exchangeHistoryStrings = new ArrayList<>();

        for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
            receiverId = itemSnapshot.child("receiverID").getValue(String.class);

            if (receiverId != null && receiverId.equals(userEmailAddress)) {
                DatabaseReference providerItemsRef = itemSnapshot.getRef().getParent();

                providerItemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot providerItemsSnapshot) {
                        productName = providerItemsSnapshot.child("productName").getValue(String.class);
                        transactionDate = providerItemsSnapshot.child("transactionDate").getValue(String.class);
                        cost = providerItemsSnapshot.child("approxMarketValue").getValue(String.class);
                        exchangeItem = providerItemsSnapshot.child("preferredExchange").getValue(String.class);
                        location = providerItemsSnapshot.child("placeOfExchange").getValue(String.class);
                        providerId = providerItemsSnapshot.child("providerID").getValue(String.class);

                        String itemDetails = "Product Name: " + productName +
                                "\nTransaction Date: " + transactionDate +
                                "\nCost: " + cost +
                                "\nExchange Item: " + exchangeItem +
                                "\nLocation: " + location +
                                "\nProvider ID/Username: " + providerId;

                        exchangeHistoryStrings.add(itemDetails);
                        updateAdapterWithData(exchangeHistoryStrings);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the database error
                    }
                });
            }
        }
    }

    private void updateAdapterWithData(List<String> exchangeHistoryStrings) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(ExchangeHistoryActivity.this,
                android.R.layout.simple_list_item_1, exchangeHistoryStrings);

        if (exchangeHistoryList != null) {
            exchangeHistoryList.setAdapter(adapter);
        } else {
            Log.e("ExchangeHistoryActivity", "exchangeHistoryList is null");
        }
    }

        protected void switch2StatPage() {
        Intent intent = new Intent(this, UserStats.class);
        startActivity(intent);
        finish();
        }
}
