package com.example.onlinebartertrader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
/**
 This class represents the activity for rating a receiver.
 It extends AppCompatActivity and implements View.OnClickListener interface.
 It contains fields for FirebaseDatabase, DatabaseReference, receiver email, rating, user email address, and item key.
 It also contains TextViews for displaying the email, current rating, and rating to be submitted, as well as buttons for submitting and canceling the rating.
 */
public class ReceiverRating extends AppCompatActivity implements View.OnClickListener {
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    String receiverEmail;
    String receiverRating;
    String userEmailAddress;
    String itemKey;

    TextView email;
    TextView rating;
    TextView currRating;
    Button submit;
    Button cancel;

    /**
     This method is called when the ReceiverRating activity is created.
     It sets up the layout, retrieves information from the Intent extra, and initializes the Firebase Database.
     It also sets up the UI elements such as text views and buttons, and assigns click listeners to the buttons.
     @param savedInstanceState The saved instance state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_rating2);

        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");

        receiverEmail = getIntent().getStringExtra("receiverEmail");
        receiverRating = getIntent().getStringExtra("receiverRating");
        userEmailAddress = getIntent().getStringExtra("userEmailAddress");
        itemKey = getIntent().getStringExtra("itemKey");

        if (userEmailAddress == null)
            userEmailAddress = "test@dalca";
        if (itemKey == null)
            itemKey = "-1";
        databaseReference = database.getReference("Users").child("Provider").child(userEmailAddress).child("items").child(itemKey);

        email = findViewById(R.id.ReceiverEmailV);
        rating = findViewById(R.id.ReceiverRatingAvgV);
        currRating = findViewById(R.id.CurrRatingInputV);
        submit = findViewById(R.id.SubmitRatingV);
        cancel = findViewById(R.id.CancelRatingV);

        String receiverEmailString = "Receiver Email: " + receiverEmail;
        String receiverRatingString = "Receiver Rating: " + receiverRating;

        email.setText(receiverEmailString);
        rating.setText(receiverRatingString);

        submit.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    /**
     This method is called when a view has been clicked. It determines which button was clicked by
     the user and performs the appropriate action. If the Submit button is clicked, it gets the value
     entered by the user in the rating field, validates it, and saves it to the Firebase Realtime Database
     under the provider's item node. If the Cancel button is clicked, the activity is finished and the
     user is taken back to the previous screen.
     @param view The view that was clicked
     */
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.SubmitRatingV) {
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
            receiverRating = Double.toString(currRatingValue);

            databaseReference.child("receiverRating").setValue(receiverRating);

            finish();
        } else if (id == R.id.CancelRatingV) {
            finish();
        }
    }
}