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