package com.example.onlinebartertrader;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
/**
 UserInfo is an Activity class that displays the user's historical information about trades.
 this is shared between provider and receiver
 */
public class UserInfo extends AppCompatActivity implements View.OnClickListener{

    //firebase
    FirebaseDatabase database;
    DatabaseReference providerDBRef;
    DatabaseReference receiverDBRef;

    String userEmailAddress;
    String userLoggedIn;

    ArrayList<Integer> valuationListProvider = new ArrayList<>();
    ArrayList<Integer> valuationListReceiver = new ArrayList<>();
    ArrayList<Float> ratingListProvider = new ArrayList<>();
    ArrayList<Float> ratingListReceiver = new ArrayList<>();

    Button exchangeHistory;

    /**
     Activity to display user's statistics, including total valuation of items sold and average rating.
     It retrieves data from Firebase Realtime Database based on the user's email address and user type
     (Provider or Receiver).
     @param savedInstanceState object containing the activity's previously saved state, if any
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");

        userLoggedIn = getIntent().getStringExtra("userLoggedIn");
        userEmailAddress = getIntent().getStringExtra("emailAddress");

        exchangeHistory = findViewById(R.id.exchangeHistoryBtn);
        exchangeHistory.setOnClickListener(this);

        if (userEmailAddress == null)
            userEmailAddress = "test@dalca";
        providerDBRef = database.getReference("Users").child("Provider").child(userEmailAddress).child("items");
        receiverDBRef = database.getReference("Users").child("Provider");
        if (userLoggedIn == null)
            userLoggedIn = "Provider";
        if(userLoggedIn.equals("Provider")){
            providerDBRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                        String currentStatus = itemSnapshot.child("currentStatus").getValue(String.class);
                        if (currentStatus != null && currentStatus.equals("Sold Out")) {
                            String approxMarketValue = itemSnapshot.child("approxMarketValue").getValue(String.class);
                            int value = Integer.parseInt(approxMarketValue);
                            valuationListProvider.add(value);

                            String providerRating = itemSnapshot.child("providerRating").getValue(String.class);
                            if (providerRating != null){
                                float rating = Float.parseFloat(providerRating);
                                ratingListProvider.add(rating);
                            }
                        }
                    }
                    int valuation = 0;
                    for (int i = 0; i < valuationListProvider.size(); i++) {
                        valuation += valuationListProvider.get(i);
                    }
                    TextView valuationView = findViewById(R.id.valuationView);
                    valuationView.setText(String.valueOf(valuation));

                    int sum =0;
                    for (int i = 0; i < ratingListProvider.size(); i++) {
                        sum += ratingListProvider.get(i);
                    }
                    float mean = (float)sum / ratingListProvider.size();
                    RatingBar ratingBar = findViewById(R.id.ratingBar);
                    TextView ratingValueTextView = findViewById(R.id.rating);

                    ratingBar.setRating(mean);
                    ratingValueTextView.setText(String.format("%.1f", mean));
                    ratingBar.setIsIndicator(true);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        else {
            receiverDBRef.addValueEventListener(new ValueEventListener() {
                @SuppressLint("DefaultLocale")
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot providerSnapshot : dataSnapshot.getChildren()) {
                        DataSnapshot itemsSnapshot = providerSnapshot.child("items");
                        for (DataSnapshot itemSnapshot : itemsSnapshot.getChildren()) {
                            String currentStatus = itemSnapshot.child("currentStatus").getValue(String.class);
                            String receiverID = itemSnapshot.child("receiverID").getValue(String.class);
                            if (currentStatus != null && receiverID != null && currentStatus.equals("Sold Out") && receiverID.equalsIgnoreCase(userEmailAddress)) {
                                String approxMarketValue = itemSnapshot.child("receiverEnteredPrice").getValue(String.class);
                                int value = Integer.parseInt(approxMarketValue);
                                valuationListReceiver.add(value);

                                String reciverRatinng = itemSnapshot.child("receiverRating").getValue(String.class);
                                if (reciverRatinng != null){
                                    float rating = Float.parseFloat(reciverRatinng);
                                    ratingListReceiver.add(rating);
                                }
                            }
                        }
                        int valuation = 0;
                        for (int i = 0; i < valuationListReceiver.size(); i++) {
                            valuation += valuationListReceiver.get(i);
                        }
                        TextView valuationView = findViewById(R.id.valuationView);
                        valuationView.setText(String.valueOf(valuation));

                        int sum =0;
                        for (int i = 0; i < ratingListReceiver.size(); i++) {
                            sum += ratingListReceiver.get(i);
                        }
                        float mean = (float)sum / ratingListReceiver.size();
                        RatingBar ratingBar = findViewById(R.id.ratingBar);
                        TextView ratingValueTextView = findViewById(R.id.rating);

                        ratingBar.setRating(mean);
                        ratingValueTextView.setText(String.format("%.1f", mean));
                        ratingBar.setIsIndicator(true);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    /**
     * Implementation of the onClick method, which is called when a view (e.g. a button) is clicked.
     * If the view with ID R.id.exchangeHistoryBtn is clicked, it creates an intent to start the ExchangeHistoryActivity class
     * and passes the email address and user type information as extras to the intent, and starts the activity.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.exchangeHistoryBtn){
            Intent intent = new Intent(this, ExchangeHistoryActivity.class);
            intent.putExtra("emailAddress", userEmailAddress.toLowerCase());
            intent.putExtra("userType", userLoggedIn);
            startActivity(intent);
        }
    }

    /**
     Check if the given rating from the database matches the given rating.
     @param ratingFromDB The rating retrieved from the database.
     @param rating The rating to be checked.
     @return true if the given rating from the database matches the given rating, false otherwise.
     */
    boolean checkGivenRating(String ratingFromDB, String rating) {
        return ratingFromDB.equalsIgnoreCase(rating);
    }

    /**
     This method checks if the total valuation amount matches the valuation amount retrieved from the database.
     @param valuationFromDB The valuation amount retrieved from the database.
     @param TotalValue The total valuation amount to check against the valuation amount retrieved from the database.
     @return True if the total valuation amount matches the valuation amount retrieved from the database, false otherwise.
     */
    boolean checkTotalAmount(String valuationFromDB, String TotalValue) {
        return valuationFromDB.equalsIgnoreCase(TotalValue);
    }
}
