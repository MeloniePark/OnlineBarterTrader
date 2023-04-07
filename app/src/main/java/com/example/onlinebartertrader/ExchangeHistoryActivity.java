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

/*
*
 * The data fetching order is as follows:
 *
 * The app first checks if the user is a Provider or Receiver based on the userType.
 * If the user is a Provider, it fetches the data related to that provider under Users > Provider > providerID > items.
 * It then loops through each item and checks if the currentStatus of the item is "Sold Out".
 * If the currentStatus is "Sold Out", it fetches the item details such as productName, transactionDate, approxMarketValue, preferredExchange, placeOfExchange, providerID, and receiverID.
 * It then adds these details to the exchangeHistoryStrings list and updates the adapter to display the exchange history in the exchangeHistoryList ListView.
 * If the user is a Receiver, it loops through all the providers under Users > Provider and fetches the items under each provider.
 * It then checks if the receiverID of the item matches the userEmailAddress of the current Receiver user.
 * If there's a match, it fetches the item details such as productName, transactionDate, approxMarketValue, preferredExchange, placeOfExchange, and providerID.
 * It then adds these details to the exchangeHistoryStrings list and updates the adapter to display the exchange history in the exchangeHistoryList ListView.
*/


public class ExchangeHistoryActivity extends AppCompatActivity {

    ListView exchangeHistoryList;
    DatabaseReference exchangeHistoryRef;
    DatabaseReference providerItemsRef;
    DatabaseReference receiverItemsRef;
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

    /**
     * This method is called when the Exchange History Activity screen is created.
     * It sets up the exchange history list view and gets a reference to the exchange history database node.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchangehistory);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");

        // Retrieve the user type and the user Email Address from the intent extra
        userType = getIntent().getStringExtra("userType");
        userEmailAddress = getIntent().getStringExtra("emailAddress");

        //Set up the exchange history list view
        exchangeHistoryList = findViewById(R.id.exchange_history_list_view);

        //Get a reference to the exchange history database node based on the user type
        setExchangeHistoryRef(userType,userEmailAddress,database);

// We do not have to use return button, but I leave it here for other page.
//        //Get a reference to the back button
//        backButton = findViewById(R.id.backToStat);
//
//        //Set a click listener for the back button
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Call the switch2StatPage() method to return to the User status page
//                switch2StatPage();
//            }
//        });
    }

    /**
     * This method checks if the exchange history list view is null.
     * If it is null, an error message is displayed and false is returned.
     * If it is not null, true is returned.
     * @return true if the exchange history list view is not null; false otherwise.
     */
    public boolean exchangeHistoryListIsNotNull() {
        if (exchangeHistoryList.getAdapter() == null) {
            errorMessage = "Exchange history list is null".trim();
            setStatusMessage(errorMessage);
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    /**
     * This method checks if the exchange history reference is null.
     * If it is null, an error message is displayed and false is returned.
     * If it is not null, true is returned.
     * @return true if the exchange history reference is not null; false otherwise.
     */
    public boolean exchangeHistoryRefIsNotNull() {
        if (exchangeHistoryRef == null) {
            errorMessage = "Exchange history reference is null".trim();
            setStatusMessage(errorMessage);
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    /**
     * Checks if a specific exchange history item is displayed in the exchange history list view.*/
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

    /**
     * Sets the status message in the error message label.
     * @param message The message to be set as the status message.
     */
    protected void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.errorMessage);
        statusLabel.setText(message.trim());
    }

    /**
     * Sets the exchange history reference for a given user.
     * @param userType The type of user ("Provider" or "Receiver").
     * @param userEmailAddress The email address of the user.
     * @param database The Firebase database.
     */
    public void setExchangeHistoryRef(String userType, String userEmailAddress, FirebaseDatabase database) {
        //Check if the user is a Provider or Receiver and fetch the respective data
        if (userType != null && userEmailAddress != null && userType.equals("Provider")) {
            exchangeHistoryRef = database.getReference("Users").child("Provider").child(userEmailAddress).child("items");
            exchangeHistoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        fetchProviderData(dataSnapshot);
                    } else {
                        Log.e("ExchangeHistoryActivity", "Data snapshot is null");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    //Handle the database error
                    Log.e("ExchangeHistoryActivity", "Database error occurred", databaseError.toException());
                }
            });
        } else if (userType != null && userEmailAddress != null && userType.equals("Receiver")){
            providerItemsRef = database.getReference("Users").child("Provider");
            providerItemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        for (DataSnapshot providerSnapshot : dataSnapshot.getChildren()) {
                            DataSnapshot itemsSnapshot = providerSnapshot.child("items");
                            fetchReceiverData(itemsSnapshot);
                        }
                    } else {
                    Log.e("ExchangeHistoryActivity", "Data snapshot is null");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    //Handle the database error
                    Log.e("ExchangeHistoryActivity", "Database error occurred", databaseError.toException());
                }
            });
        }
    }

    /**
     * Fetches data from Firebase Realtime Database for provider items that have been marked as "Sold Out"
     * and adds the information to a list of strings to be displayed in the Exchange History activity.
     * @param dataSnapshot a DataSnapshot object containing the provider's items data in the database
     */
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
                        "\nReceiver ID: " + receiverId;

                exchangeHistoryStrings.add(itemDetails);
            }
        }
        updateAdapterWithData(exchangeHistoryStrings);
    }

    /**
     * Fetches data from Firebase Realtime Database for items received by the user and adds the relevant
     * information to a list of strings to be displayed in the Exchange History activity.
     * @param dataSnapshot a DataSnapshot object containing the receiver's items data in the database
     */
    private void fetchReceiverData(DataSnapshot dataSnapshot) {
        List<String> exchangeHistoryStrings = new ArrayList<>();

        for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
            receiverId = itemSnapshot.child("receiverID").getValue(String.class);

            if (receiverId != null && receiverId.equals(userEmailAddress)) {
                receiverItemsRef = itemSnapshot.getRef();

                receiverItemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot receiverItemsSnapshot) {
                        if (receiverItemsSnapshot.getValue() != null) {
                            productName = receiverItemsSnapshot.child("productName").getValue(String.class);
                            transactionDate = receiverItemsSnapshot.child("transactionDate").getValue(String.class);
                            cost = receiverItemsSnapshot.child("approxMarketValue").getValue(String.class);
                            exchangeItem = receiverItemsSnapshot.child("preferredExchange").getValue(String.class);
                            location = receiverItemsSnapshot.child("placeOfExchange").getValue(String.class);
                            providerId = receiverItemsSnapshot.child("providerID").getValue(String.class);

                            String itemDetails = "Product Name: " + productName +
                                    "\nTransaction Date: " + transactionDate +
                                    "\nCost: " + cost +
                                    "\nExchange Item: " + exchangeItem +
                                    "\nLocation: " + location +
                                    "\nProvider ID: " + providerId;

                            exchangeHistoryStrings.add(itemDetails);
                            updateAdapterWithData(exchangeHistoryStrings);
                        } else {
                            Log.e("ExchangeHistoryActivity", "Data snapshot is null");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the database error
                    }
                });
            }
        }
    }

    /**
     * Updates the adapter of the Exchange History activity with the list of exchange history strings.
     * @param exchangeHistoryStrings a List object containing strings of exchange history information
     */
    private void updateAdapterWithData(List<String> exchangeHistoryStrings) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(ExchangeHistoryActivity.this,
                android.R.layout.simple_list_item_1, exchangeHistoryStrings);

        if (exchangeHistoryList != null) {
            exchangeHistoryList.setAdapter(adapter);
        } else {
            Log.e("ExchangeHistoryActivity", "exchangeHistoryList is null");
        }
    }

/*   we do not have to use the return button, but I leave it here for other pages.

     //Launches and switch the UserStats activity and finishes the current activity.

        protected void switch2StatPage() {
        Intent intent = new Intent(this, UserStats.class);
        startActivity(intent);
        finish();
        }
    */
}
