package com.example.onlinebartertrader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReceiverRating extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_rating2);
        ProviderRatingReceiverObj providerRatingReceiverObj = new ProviderRatingReceiverObj("tn785083@dal.ca","45");
        providerRatingReceiverObj.getDBInfo();
        providerRatingReceiverObj.addNewRating(2);
        providerRatingReceiverObj.addNewRating(3);
        providerRatingReceiverObj.addNewRating(4);
    }
}