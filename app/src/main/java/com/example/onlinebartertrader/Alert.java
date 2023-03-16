package com.example.onlinebartertrader;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Alert {

    protected ValueEventListener userPreferenceLisenter;

    protected FirebaseDatabase database;
    protected DatabaseReference receiverDBRef;
    protected String userEmailAddress;

    public Alert() {
    }

    public Alert(String userEmail) {
        this.userEmailAddress = userEmail;
        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");
        String itemRef = "Users/Receiver/" + userEmailAddress + "/preference/";
        receiverDBRef = database.getReference(itemRef);
    }

    public void startListening() {
    }

    public void stopListening() {
    }

    boolean isItemAvailable(Date itemAvailDate) {
        return false;
    }

    boolean isUserInterested(String itemType) {
        return false;
    }

    private void sendNotification(String item) {
    }
}
