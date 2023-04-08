package com.example.onlinebartertrader;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

// This class extends the AppCompatActivity and implements the View.OnClickListener interface
public class UserInfo extends AppCompatActivity implements View.OnClickListener{

    // Firebase instance variables
    FirebaseDatabase database; // Database instance
    DatabaseReference providerDBRef; // Reference to the Provider node in the database
    DatabaseReference receiverDBRef; // Reference to the Receiver node in the database

    // User email and login variables
    String userEmailAddress;
    String userLoggedIn;

    // Lists to store the valuation and rating values for Provider and Receiver
    ArrayList<Integer> valuationListProvider = new ArrayList<>();
    ArrayList<Integer> valuationListReceiver = new ArrayList<>();
    ArrayList<Float> ratingListProvider = new ArrayList<>();
    ArrayList<Float> ratingListReceiver = new ArrayList<>();

    //String literals
    private static final String STR_PROVIDER = "Provider";

    // The onCreate() method is called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats); // Set the layout for this activity
        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/"); // Get the Firebase database instance

        // Get the user email and login information from the intent that started this activity
        userLoggedIn = getIntent().getStringExtra("userLoggedIn");
        userEmailAddress = getIntent().getStringExtra("emailAddress");

        // If the email address is null, set it to a default value
        if (userEmailAddress == null)
            userEmailAddress = "test@dalca";

        // Get the references to the Provider and Receiver nodes in the database
        providerDBRef = database.getReference("Users").child(STR_PROVIDER).child(userEmailAddress).child("items");
        receiverDBRef = database.getReference("Users").child(STR_PROVIDER);
        if (userLoggedIn == null)
            userLoggedIn = STR_PROVIDER;
        // If the user is logged in as a Provider, add a value event listener to the Provider node in the database
        if(userLoggedIn.equals(STR_PROVIDER)){
            providerDBRef.addValueEventListener(new ValueEventListener() {
                // This method is called when the data at the Provider node in the database changes
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Loop through each child node of the Provider node in the database
                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                        // Get the current status of the item
                        String currentStatus = itemSnapshot.child("currentStatus").getValue(String.class);
                        // If the item is sold out, get its approximate market value and add it to the valuation list
                        if (currentStatus != null && currentStatus.equals("Sold Out")) {
                            String approxMarketValue = itemSnapshot.child("approxMarketValue").getValue(String.class);
                            int value = Integer.parseInt(approxMarketValue);
                            valuationListProvider.add(value);

                            // Get the provider rating for the item and add it to the rating list
                            String providerRating = itemSnapshot.child("providerRating").getValue(String.class);
                            if (providerRating != null){
                                float rating = Float.parseFloat(providerRating);
                                ratingListProvider.add(rating);
                            }
                        }
                    }
                    // Update the valuation and rating views on the activity layout with the Provider's valuation and rating lists
                    updateValuationAndRating(valuationListProvider, ratingListProvider, findViewById(R.id.valuationView), findViewById(R.id.ratingBar), findViewById(R.id.rating));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    //Not used in this iteration, but kept for future implementation
                }
            });
        }

        else {
            receiverDBRef.addValueEventListener(new ValueEventListener() {
                @SuppressLint("DefaultLocale")
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Looping through each provider snapshot in the data snapshot
                    for (DataSnapshot providerSnapshot : dataSnapshot.getChildren()) {
                        // Getting the "items" snapshot for the current provider snapshot
                        DataSnapshot itemsSnapshot = providerSnapshot.child("items");
                        // Looping through each item snapshot in the items snapshot
                        for (DataSnapshot itemSnapshot : itemsSnapshot.getChildren()) {
                            // Getting the current status and receiver ID for the current item snapshot
                            String currentStatus = itemSnapshot.child("currentStatus").getValue(String.class);
                            String receiverID = itemSnapshot.child("receiverID").getValue(String.class);
                            // Checking if the item is sold out and belongs to the current user
                            if (currentStatus != null && receiverID != null && currentStatus.equals("Sold Out") && receiverID.equals(userEmailAddress)) {
                                // Getting the approximate market value for the item and adding it to the receiver's valuation list
                                String approxMarketValue = itemSnapshot.child("receiverEnteredPrice").getValue(String.class);
                                int value = Integer.parseInt(approxMarketValue);
                                valuationListReceiver.add(value);

                                // Getting the receiver's rating for the item and adding it to the receiver's rating list
                                String reciverRatinng = itemSnapshot.child("receiverRating").getValue(String.class);
                                if (reciverRatinng != null){
                                    float rating = Float.parseFloat(reciverRatinng);
                                    ratingListReceiver.add(rating);
                                }
                            }
                        }
                        // Updating the valuation and rating display for the receiver
                        updateValuationAndRating(valuationListReceiver, ratingListReceiver, findViewById(R.id.valuationView), findViewById(R.id.ratingBar), findViewById(R.id.rating));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    //Not used in this iteration, but kept for future implementation
                }
            });
        }

    }

    private void updateValuationAndRating(List<Integer> valuationList, List<Float> ratingList, TextView valuationView, RatingBar ratingBar, TextView ratingValueTextView) {
        // Calculating the total valuation of all items in the valuation list
        int valuation = 0;
        for (int i = 0; i < valuationList.size(); i++) {
            valuation += valuationList.get(i);
        }
        // Setting the valuation view to display the total valuation
        valuationView.setText(String.valueOf(valuation));

        // Calculating the mean rating of all items in the rating list
        int sum = 0;
        for (int i = 0; i < ratingList.size(); i++) {
            sum += ratingList.get(i);
        }
        float mean = ratingList.isEmpty() ? 0 : (float)sum / ratingList.size();
        // Setting the rating bar and rating value text view to display the mean rating
        ratingBar.setRating(mean);
        ratingValueTextView.setText(String.format("%.1f", mean));
        ratingBar.setIsIndicator(true);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.exchangeHistoryBtn){
            // Opening the user info activity when the exchange history button is clicked
            Intent intent = new Intent(this, UserInfo.class);
            intent.putExtra("emailAddress", userEmailAddress.toLowerCase());
            intent.putExtra("userLoggedIn", userLoggedIn);
            startActivity(intent);
        }
    }

    boolean checkGivenRating(String ratingFromDB, String rating) {
        // Checking if the given rating matches the rating from the database
        return ratingFromDB.equalsIgnoreCase(rating);
    }

    boolean checkTotalAmount(String valuationFromDB, String value) {
        // Checking if the given value matches the value from the database
        return valuationFromDB.equalsIgnoreCase(value);
    }
}
