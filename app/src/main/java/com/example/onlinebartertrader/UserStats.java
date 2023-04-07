package com.example.onlinebartertrader;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserStats extends AppCompatActivity implements View.OnClickListener{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");

        userLoggedIn = getIntent().getStringExtra("userLoggedIn");
        userEmailAddress = getIntent().getStringExtra("emailAddress");

        providerDBRef = database.getReference("Users").child("Provider").child(userEmailAddress).child("items");
        receiverDBRef = database.getReference("Users").child("Provider");

        valuationListProvider = new ArrayList<>();
        valuationListReceiver = new ArrayList<>();

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
                        }
                    }
                    int valuation = 0;
                    for (int i = 0; i < valuationListProvider.size(); i++) {
                        valuation += valuationListProvider.get(i);
                    }
                    TextView valuationView = findViewById(R.id.valuationView);
                    valuationView.setText(String.valueOf(valuation));

                    RatingBar ratingBar = findViewById(R.id.ratingBar);
                    TextView ratingValueTextView = findViewById(R.id.rating);

                    ratingBar.setRating(3.7f);
                    ratingValueTextView.setText(String.format("%.1f", ratingBar.getRating()));
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
                            if (currentStatus != null && receiverID != null && currentStatus.equals("Sold Out") && receiverID.equals(userEmailAddress)) {
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.exchangeHistoryBtn){
            Intent intent = new Intent(this, UserStats.class);
            intent.putExtra("emailAddress", userEmailAddress.toLowerCase());
            intent.putExtra("userLoggedIn", userLoggedIn);
            startActivity(intent);
        }
    }

    boolean checkGivenRating(String ratingFromDB, String rating) {
        return ratingFromDB.equalsIgnoreCase(rating);
    }

    boolean checkTotalAmount(String valuationFromDB, String TotalValue) {
        return valuationFromDB.equalsIgnoreCase(TotalValue);
    }
}
