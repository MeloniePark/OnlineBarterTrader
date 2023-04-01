package com.example.onlinebartertrader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    String receiverAvgRating;
    String receiverTotalRating;
    String receiverTotalRatingNum;
    String userEmailAddress;
    String itemKey;

    TextView email;
    TextView avg;
    TextView currRating;
    TextView submit;
    TextView cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_rating2);

        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");

        receiverEmail = getIntent().getStringExtra("receiverEmail");
        receiverAvgRating = getIntent().getStringExtra("receiverAvgRating");
        receiverTotalRating = getIntent().getStringExtra("receiverTotalRating");
        receiverTotalRatingNum = getIntent().getStringExtra("receiverTotalRatingNum");
        userEmailAddress = getIntent().getStringExtra("userEmailAddress");
        itemKey = getIntent().getStringExtra("itemKey");

        databaseReference = database.getReference("Users").child("Provider").child(userEmailAddress).child("items").child(itemKey);

        email = findViewById(R.id.ReceiverEmailV);
        avg = findViewById(R.id.ReceiverRatingAvgV);
        currRating = findViewById(R.id.CurrRatingInputV);
        submit = findViewById(R.id.SubmitRatingV);
        cancel = findViewById(R.id.CancelRatingV);

        String receiverEmailString = "Receiver Email: " + receiverEmail;
        String receiverAvgRatingString = "Receiver Average Rating: " + receiverAvgRating;

        email.setText(receiverEmailString);
        avg.setText(receiverAvgRatingString);

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
            double total = Double.parseDouble(receiverTotalRating) + currRatingValue;
            int totalNum = Integer.parseInt(receiverTotalRatingNum) + 1;
            double avgNum = Math.round((total/totalNum)*10)/10.0;
            receiverAvgRating = Double.toString(avgNum);
            receiverTotalRating = Double.toString(total);
            receiverTotalRatingNum = Integer.toString(totalNum);

            databaseReference.child("productReceiverAvgRating").setValue(receiverAvgRating);
            databaseReference.child("productReceiverTotalRating").setValue(receiverTotalRating);
            databaseReference.child("productReceiverTotalRatingNum").setValue(receiverTotalRatingNum);

            finish();
        } else if (id == R.id.CancelRatingV) {
            finish();
        }
    }
}