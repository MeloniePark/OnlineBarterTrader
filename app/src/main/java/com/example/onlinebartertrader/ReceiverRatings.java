package com.example.onlinebartertrader;

import static com.example.onlinebartertrader.R.id.ratingBar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReceiverRatings extends AppCompatActivity {

    private RatingBar ratingBar;
    private TextView textViewRating;
    private Button submitRatingButton;
    private FirebaseDatabase database;
    private DatabaseReference ratingsReference;
    private String userEmailAddress;
    private String providerEmailAddress;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiverrating);

        ratingBar = findViewById(R.id.ratingBar);
        textViewRating = findViewById(R.id.textViewRating);
        submitRatingButton = findViewById(R.id.ratingBar);

        // Initialize the database and reference
        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");
        ratingsReference = database.getReference("Users/Provider");

        // Get user and provider email addresses from the intent
        userEmailAddress = getIntent().getStringExtra("emailAddress");
        providerEmailAddress = getIntent().getStringExtra("providerEmailAddress");

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
        float rating = ratingBar.getRating();
        ratingsReference.child(providerEmailAddress).child("ratings").child(userEmailAddress).setValue(rating);

        Toast.makeText(ReceiverRatings.this, "Rating submitted successfully!", Toast.LENGTH_SHORT).show();

        // Redirect to another activity after submitting the rating, e.g., ReceiverLandingPage
        Intent intent = new Intent(ReceiverRatings.this, ReceiverLandingPage.class);
        intent.putExtra("emailAddress", userEmailAddress);
        startActivity(intent);
        finish();
    }
}