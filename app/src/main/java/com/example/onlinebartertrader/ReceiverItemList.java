package com.example.onlinebartertrader;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.*;



public class ReceiverItemList {
    protected FirebaseDatabase database;
    protected DatabaseReference receiverDBRef;
    DatabaseReference providerDBRef;
    protected String userEmailAddress;
    protected ListView myListView;
    protected ListView receiverLists;
    protected Context myContext;
    DatabaseReference receiverDBRefAvailable;
    ArrayAdapter<String> receiverArrAdapter;
    ArrayList<String> receiverItems = new ArrayList<>();

    //logger - logging is better exercise than system printing out.
    private static final Logger logger = Logger.getLogger(Alert.class.getName());

    public ReceiverItemList() {
    }

    public ReceiverItemList(String userEmail, ListView receiverLists, Context myContext) {
        this.receiverLists = receiverLists;
        this.userEmailAddress = userEmail.toLowerCase(Locale.ROOT);
        this.myContext = myContext;
        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");
    }

    public void startListening() {

        //array Adapter for the listview to list all the items of the provider.
        ArrayAdapter<String> receiverArrAdapter = new ArrayAdapter<String>
                (myContext, android.R.layout.simple_list_item_1, receiverItems);

        receiverLists.setAdapter(receiverArrAdapter);

        providerDBRef = database.getReference("Users/Provider");
        providerDBRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @com.google.firebase.database.annotations.Nullable String s) {
                String providerEmail = dataSnapshot.getKey();

                boolean sameUser = checkItemIsPostedByTheReceiver(providerEmail, userEmailAddress);
                DatabaseReference providerItemsRef = database.getReference("Users/Provider/" + providerEmail + "/items");


                //set item click listener
                receiverLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
//                        String item1 = receiverArrAdapter.getItem(index);
//                        long itemId = receiverArrAdapter.getItemId(index);
//                        System.out.println(providerEmail);
//                        Intent myIntent = new Intent(view.getContext(), ItemActivity.class);
//                        myIntent.putExtra("providerID", providerEmail);
//                        myIntent.putExtra("receiverID", userEmailAddress);
//                        view.getContext().startActivity(myIntent);

                        String clickedItem = (String) adapterView.getItemAtPosition(index);
                        // NOTE: I changed the string to add the item id and other stuffs here as well.
                        String[] itemParts = clickedItem.split(", ");
                        String itemID = itemParts[0].substring(9);

                        DatabaseReference itemsRef = database.getReference("Users/Provider");
                        itemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot providerSnapshot : dataSnapshot.getChildren()) {
                                    DataSnapshot itemsSnapshot = providerSnapshot.child("items");
                                    for (DataSnapshot itemSnapshot : itemsSnapshot.getChildren()) {
                                        String currentItemID = itemSnapshot.getKey();
                                        assert currentItemID != null;
                                        if (currentItemID.equalsIgnoreCase(itemID)) {
                                            // The item was found in the database. can also get other item if nessesary
                                            String itemType = itemSnapshot.child("productType").getValue(String.class);
                                            String itemName = itemSnapshot.child("productName").getValue(String.class);
                                            String itemPoviderEmail = providerSnapshot.getKey();
                                            System.out.println(itemPoviderEmail);

                                            Intent myIntent = new Intent(view.getContext(), ItemActivity.class);
                                            myIntent.putExtra("itemID", itemID);
                                            myIntent.putExtra("itemName", itemName);
                                            myIntent.putExtra("itemType", itemType);
                                            myIntent.putExtra("providerEmail", itemPoviderEmail);
                                            myIntent.putExtra("receiverEmail", userEmailAddress);
                                            view.getContext().startActivity(myIntent);
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {}
                        });
                    }
                });


                if (!sameUser) {
                    providerItemsRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @com.google.firebase.database.annotations.Nullable String s) {
                            String itemType = snapshot.child("productType").getValue(String.class);
                            String itemName = snapshot.child("productName").getValue(String.class);
                            String itemPlace = snapshot.child("placeOfExchange").getValue(String.class);
                            String itemID = snapshot.getKey();

                            receiverItems.add("Item ID: " + itemID +", Item Name: " + itemName + ", Item Type: " + itemType + ", Provider: "+ providerEmail +
                                    ", Place: " + itemPlace);
                            receiverArrAdapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @com.google.firebase.database.annotations.Nullable String s) {
                            receiverArrAdapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                            //this method was left empty for possible future implementation requirements
                            throw new UnsupportedOperationException("No support for this operation in iteration 2");

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @com.google.firebase.database.annotations.Nullable String s) {
                            //this method was left empty for possible future implementation requirements
                            throw new UnsupportedOperationException("No support for this operation in iteration 2");

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError snapshot) {
                            //this method was left empty for possible future implementation requirements
                            throw new UnsupportedOperationException("No support for this operation in iteration 2");

                        }

                    });
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @com.google.firebase.database.annotations.Nullable String s) {
                //this method was left empty for possible future implementation requirements
                throw new UnsupportedOperationException("No support for this operation in iteration 2");

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                //this method was left empty for possible future implementation requirements
                throw new UnsupportedOperationException("No support for this operation in iteration 2");
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @com.google.firebase.database.annotations.Nullable String s) {
                //this method was left empty for possible future implementation requirements
                throw new UnsupportedOperationException("No support for this operation in iteration 2");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //this method was left empty for possible future implementation requirements
                throw new UnsupportedOperationException("No support for this operation in iteration 2");
            }

        });

    }

    boolean checkItemIsPostedByTheReceiver(String receiverEmail, String providerEmail) {
        return receiverEmail.equalsIgnoreCase(providerEmail);
    }

}
