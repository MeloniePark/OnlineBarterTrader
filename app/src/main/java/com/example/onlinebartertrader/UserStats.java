package com.example.onlinebartertrader;

import android.content.Intent;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
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

public class UserStats extends AppCompatActivity implements View.OnClickListener{

    //firebase
    FirebaseDatabase database;
    DatabaseReference userDBRef;

    String userEmailAddress;
    String userLoggedIn;

    ArrayList<Integer> valuationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");

        userLoggedIn = getIntent().getStringExtra("userLoggedIn");
        userEmailAddress = getIntent().getStringExtra("emailAddress");

        userDBRef = database.getReference("Users").child(userLoggedIn).child(userEmailAddress).child("items");

        valuationList = new ArrayList<>();

        userDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    String currentStatus = itemSnapshot.child("currentStatus").getValue(String.class);
                    if (currentStatus != null && currentStatus.equals("Sold Out")) {
                        String approxMarketValue = itemSnapshot.child("approxMarketValue").getValue(String.class);
                        int value = Integer.parseInt(approxMarketValue);
                        valuationList.add(value);
                    }
                }
                int valuation = 0;
                for (int i = 0; i < valuationList.size(); i++) {
                    valuation += valuationList.get(i);
                }
                TextView valuationView = findViewById(R.id.valuationView);
                valuationView.setText(String.valueOf(valuation));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
