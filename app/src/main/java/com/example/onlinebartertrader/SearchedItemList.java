package com.example.onlinebartertrader;

import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.*;

public class SearchedItemList extends Activity {
    protected FirebaseDatabase database;
    protected DatabaseReference receiverDBRef;
    DatabaseReference providerDBRef;
    protected String userEmailAddress;
    protected ListView myListView;
    protected ListView receiverLists;
    protected Context myContext;
    protected String query;

    //Logging
    Logger logger = Logger.getLogger(SearchedItemList.class.getName());

    DatabaseReference receiverDBRefAvailable;
    ArrayAdapter<String> receiverArrAdapter;
    ArrayList<String> receiverItems = new ArrayList<>();
    String preference = "All";
    String userPreference;
    public SearchedItemList() {
    }

    public SearchedItemList(String userEmail, ListView receiverLists, Context myContext, String query) {
        this.receiverLists = receiverLists;
        this.userEmailAddress = userEmail.toLowerCase(Locale.ROOT);
        this.myContext = myContext;
        this.query = query;
        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");

        receiverArrAdapter = new ArrayAdapter<String>
                (myContext, android.R.layout.simple_list_item_1, receiverItems);
        receiverLists.setAdapter(receiverArrAdapter);
    }

    public void startListening() {

        //array Adapter for the listview to list all the items of the provider.
        ArrayAdapter<String> receiverArrAdapter = new ArrayAdapter<String>
                (myContext, android.R.layout.simple_list_item_1, receiverItems);

        receiverLists.setAdapter(receiverArrAdapter);

        providerDBRef = database.getReference("Users/Provider");
        DatabaseReference preferences = database.getReference("Users/Receiver/"+userEmailAddress+"/preference");

        preferences.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userPreference = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle potential errors here
            }
        });
        providerDBRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @com.google.firebase.database.annotations.Nullable String s) {
                String providerEmail = dataSnapshot.getKey();
                boolean sameUser = checkItemIsPostedByTheReceiver(providerEmail, userEmailAddress);
                DatabaseReference providerItemsRef = database.getReference("Users/Provider/" + providerEmail + "/items");
                if (!sameUser) {
                    providerItemsRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @com.google.firebase.database.annotations.Nullable String s) {
                            if(query.equals("null")) {
                                if (userPreference.equals("All")) {
                                    String itemName = snapshot.child("productName").getValue(String.class);
                                    String itemType = snapshot.child("productType").getValue(String.class);
                                    String exchangeWith = snapshot.child("preferredExchange").getValue(String.class);
                                    String location = snapshot.child("placeOfExchange").getValue(String.class);
                                    receiverItems.add("Item Name: " + itemName + "\nItem Type: " + itemType + "\nPreferred Exchange: " + exchangeWith + "\nLocation: " + location);
                                    receiverArrAdapter.notifyDataSetChanged();
                                } else {
                                    String itemType = snapshot.child("productType").getValue(String.class);
                                    if (itemType.equals(userPreference)) {
                                        String itemName = snapshot.child("productName").getValue(String.class);
                                        String exchangeWith = snapshot.child("preferredExchange").getValue(String.class);
                                        String location = snapshot.child("placeOfExchange").getValue(String.class);
                                        receiverItems.add("Item Name: " + itemName + "\nItem Type: " + itemType + "\nPreferred Exchange: " + exchangeWith + "\nLocation: " + location);
                                        receiverArrAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                            else {
                                String pattern = "(?i).*" + query + ".*";
                                String itemType = snapshot.child("productType").getValue(String.class);
                                String exchangeWith = snapshot.child("preferredExchange").getValue(String.class);
                                String location = snapshot.child("placeOfExchange").exists() ? snapshot.child("placeOfExchange").getValue(String.class) : "";
                                if (itemType.matches(pattern) || exchangeWith.matches(pattern) || location.matches(pattern)) {
                                    String itemName = snapshot.child("productName").getValue(String.class);
                                    receiverItems.add("Item Name: " + itemName + "\nItem Type: " + itemType + "\nPreferred Exchange: " + exchangeWith + "\nLocation: " + location);
                                    receiverArrAdapter.notifyDataSetChanged();
                                }
                            }
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @com.google.firebase.database.annotations.Nullable String s) {
                            receiverArrAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                            //unused method, but left for possible future implementation
                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @com.google.firebase.database.annotations.Nullable String s) {
                            //unused method, but left for possible future implementation
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError snapshot) {
                            //unused method, but left for possible future implementation
                        }

                    });
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @com.google.firebase.database.annotations.Nullable String s) {
                //unused method, but left for possible future implementation
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                //unused method, but left for possible future implementation
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @com.google.firebase.database.annotations.Nullable String s) {
                //unused method, but left for possible future implementation
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //unused method, but left for possible future implementation
            }

        });
    }

    boolean checkItemIsPostedByTheReceiver(String receiverEmail, String providerEmail) {
        return receiverEmail.equalsIgnoreCase(providerEmail);
    }
}