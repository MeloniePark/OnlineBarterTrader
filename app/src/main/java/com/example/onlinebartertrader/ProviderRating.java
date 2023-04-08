package com.example.onlinebartertrader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 This activity is responsible for displaying the rating of a provider, and allowing the receiver to rate the provider.
 The receiver can rate the provider out of 5 stars, and this rating will be stored in the Firebase Realtime Database.
 */
public class ProviderRating extends AppCompatActivity implements View.OnClickListener {
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    String receiverEmail;
    String providerEmail;
    String itemKey;

    TextView email;
    TextView rating;
    TextView currRating;
    Button submit;
//    TextView cancel;

    /**
     This activity allows a receiver to rate a provider and submit the rating to the database.
     It retrieves the provider's email, current rating, and item ID from the previous activity.
     It then displays the provider's email and current rating on the screen.
     When the submit button is clicked, it updates the provider's rating in the database.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_rating);

        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");

        receiverEmail = getIntent().getStringExtra("receiverEmail");
        providerEmail = getIntent().getStringExtra("providerEmail");
        itemKey = getIntent().getStringExtra("itemID");

        if (itemKey == null)
            itemKey = "-1";
        databaseReference = database.getReference("Users").child("Provider").child(providerEmail).child("items").child(itemKey);

        email = findViewById(R.id.ProviderEmailV);
        currRating = findViewById(R.id.ProviderCurrRatingInputV);
        submit = findViewById(R.id.ProviderSubmitRatingV);
        String providerEmailString = "Provider Email: " + providerEmail;

        email.setText(providerEmailString);

        submit.setOnClickListener(this);
    }

    /**
     * This method starts the ReceiverLandingPage activity with the email address entered by the user as an extra.
     */
    protected void switch2ReceiverLandingPage() {
        Intent intent = new Intent(this, ReceiverLandingPage.class);
        intent.putExtra("emailAddress", receiverEmail.toLowerCase());
        startActivity(intent);
    }

    /**
     Handles the click event for the "submit" button in the ProviderRating activity.
     Retrieves the current rating entered by the user, checks if it is valid,
     and updates the provider's rating in the Firebase Realtime Database.
     Switches to the ReceiverLandingPage activity using the switch2ReceiverLandingPage() method.
     @param view The view that was clicked (in this case, the "submit" button)
     */
    @Override
    public void onClick(View view) {
        String currRatingString = currRating.getText().toString().trim();
        if (currRatingString.isEmpty()) {
            // Show an error message to the user
            Toast.makeText(this, "Please enter a rating", Toast.LENGTH_SHORT).show();
            return;
        }
        double currRatingValue;
        try {
            currRatingValue = Double.parseDouble(currRatingString);
        } catch (NumberFormatException e) {
            // Show an error message to the user
            Toast.makeText(this, "Please enter a valid rating", Toast.LENGTH_SHORT).show();
            return;
        }
        currRatingString = Double.toString(currRatingValue);

        databaseReference.child("providerRating").setValue(currRatingString);

        switch2ReceiverLandingPage();

    }
}