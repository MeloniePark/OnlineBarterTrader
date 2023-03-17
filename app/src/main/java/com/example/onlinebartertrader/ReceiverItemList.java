package com.example.onlinebartertrader;

import static androidx.test.InstrumentationRegistry.getContext;
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Alert {
    protected FirebaseDatabase database;
    protected DatabaseReference receiverDBRef;
    DatabaseReference providerDBRef;
    protected String userEmailAddress;
    protected String itemInterested = "nullType";
    protected Context myContext;

    public Alert() {
    }

    public Alert(String userEmail, Context myContext) {
        this.myContext = myContext;
        this.userEmailAddress = userEmail.toLowerCase(Locale.ROOT);
        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");
        String itemRef = "Users/Receiver/" + userEmailAddress + "/preference/";
        receiverDBRef = database.getReference(itemRef);

        receiverDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                itemInterested = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Failed to read value. " + databaseError.getCode());
            }
        });

        System.out.println(userEmail);
        System.out.println(itemInterested);
    }

    public void startListening() {
        providerDBRef = database.getReference("Users/Provider");
        providerDBRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String providerEmail = dataSnapshot.getKey();
                DatabaseReference providerItemsRef = database.getReference("Users/Provider/" + providerEmail + "/items");
                providerItemsRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        String itemType = dataSnapshot.child("productType").getValue(String.class);
                        String itemAvailDateString = dataSnapshot.child("dateOfAvailability").getValue(String.class);
                        String itemName = dataSnapshot.child("productName").getValue(String.class);
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                        Date itemAvailDate = new Date();
                        try {
                            itemAvailDate = format.parse(itemAvailDateString);
                            if (isItemAvailable(itemAvailDate) && isUserInterested(itemType)) {
                                sendNotification(itemType, itemName);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        String itemType = dataSnapshot.child("productType").getValue(String.class);
                        String itemAvailDateString = dataSnapshot.child("dateOfAvailability").getValue(String.class);
                        String itemName = dataSnapshot.child("productName").getValue(String.class);
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                        Date itemAvailDate = new Date();
                        try {
                            itemAvailDate = format.parse(itemAvailDateString);
                            if (isItemAvailable(itemAvailDate) && isUserInterested(itemType)) {
                                sendNotification(itemType, itemName);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }

                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}

        });
    }

    boolean isItemAvailable(Date itemAvailDate) {
        // adapted from A3
        Calendar currentCalendar = Calendar.getInstance();
        Date currentTime = currentCalendar.getTime();

        return currentTime.after(itemAvailDate);
    }

    boolean isUserInterested(String itemType) {
        return itemType.equalsIgnoreCase(itemInterested);
    }

//    https://developer.android.com/develop/ui/views/notifications/build-notification
void sendNotification(String itemType, String itemName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
        builder.setMessage("A new item that you are interested is added or modified!\nItem Type: "
                        +itemType+"\nItem Name: "+itemName)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

}
