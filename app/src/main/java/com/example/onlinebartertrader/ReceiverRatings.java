package com.example.onlinebartertrader;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class ReceiverRatings extends AppCompatActivity {

    private RatingBar ratingBar;
    private TextView textViewRating;
    private Button submitRatingButton;
    private FirebaseDatabase database;
    private DatabaseReference ratingsReference;
    private String providerEmailAddress;
    private String itemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiverrating);

        ratingBar = findViewById(R.id.ratingBar);
        textViewRating = findViewById(R.id.textViewRating);
        submitRatingButton = findViewById(R.id.submit_rating_button);

        // Initialize the database and reference
        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");
        ratingsReference = database.getReference("Users/Provider");

        // Get provider email address and itemID from the intent
        providerEmailAddress = getIntent().getStringExtra("providerEmailAddress");
        itemID = getIntent().getStringExtra("itemID");

        fetchAndDisplayAverageRating();

        // Set the onRatingBarChangeListener
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                textViewRating.setText("Your rating: " + rating);
            }
        });

        // Set the onClickListener for the submit button
        submitRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRating();
            }
        });
    }

    private void submitRating() {
        final int ratingValue = (int) ratingBar.getRating();

        ratingsReference.child(providerEmailAddress).child("items").child(itemID)
                .runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                        float productReceiverTotalRating = mutableData.child("productReceiverTotalRating").getValue(float.class);
                        int productReceiverTotalRatingNum = mutableData.child("productReceiverTotalRatingNum").getValue(int.class);

                        // Update the total rating and the number of ratings
                        productReceiverTotalRating += ratingValue;
                        productReceiverTotalRatingNum++;

                        // Save the updated values back to the database
                        mutableData.child("productReceiverTotalRating").setValue(productReceiverTotalRating);
                        mutableData.child("productReceiverTotalRatingNum").setValue(productReceiverTotalRatingNum);

                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, boolean committed, @Nullable DataSnapshot dataSnapshot) {
                        if (committed) {
                            Toast.makeText(ReceiverRatings.this, "Rating submitted successfully.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ReceiverRatings.this, "Rating submission failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void fetchAndDisplayAverageRating() {
        ratingsReference.child(providerEmailAddress).child("items").child(itemID)
                .addValueEventListener(new ValueEventListener() {
                    @Override

                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            float productReceiverTotalRating = dataSnapshot.child("productReceiverTotalRating").getValue(float.class);
                            int productReceiverTotalRatingNum = dataSnapshot.child("productReceiverTotalRatingNum").getValue(int.class);

                            if (productReceiverTotalRatingNum != 0) {
                                float averageRating = productReceiverTotalRating / productReceiverTotalRatingNum;
                                textViewRating.setText("Current average rating: " + averageRating);
                            } else {
                                textViewRating.setText("No ratings yet.");
                            }
                        } else {
                            textViewRating.setText("No ratings data found.");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(ReceiverRatings.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}