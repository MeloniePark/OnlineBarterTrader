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
/**
 This activity displays the list of items that matched the user's search criteria.
 */
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

    /**
     Default constructor for the SearchedItemList class.
     */
    public SearchedItemList() {
    }

    /**
     Parameterized constructor for the SearchedItemList class.
     @param userEmail The email address of the user.
     @param receiverLists The ListView that displays the searched items.
     @param myContext The context of the activity that calls the SearchedItemList class.
     @param query The search query entered by the user.
     */
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

    /**
     This method starts listening for changes in the database and populates the list view with items that match the search query and user preference.
     If the search query is null, all items that match the user preference will be displayed in the list view. If the user preference is "All", all items will be displayed.
     If the search query is not null, all items that match the search query will be displayed in the list view.
     @throws NullPointerException if the required instance variables have not been initialized before calling this method
     */
    public void startListening() {

        //array Adapter for the listview to list all the items of the provider.
        ArrayAdapter<String> receiverArrAdapter = new ArrayAdapter<String>
                (myContext, android.R.layout.simple_list_item_1, receiverItems);
        if (receiverLists == null)
            receiverLists = findViewById(R.id.searchViewSearch);
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
            /**
             This method is triggered whenever a child is added to the provider database reference. It first checks whether the provider is the same as the receiver,
             and if not, it retrieves the items posted by the provider and adds them to the receiver's list view based on the receiver's preference and the search query.
             If the query is "null", it displays all the items that match the receiver's preference. Otherwise, it filters the items based on the search query,
             which can match either the product type, preferred exchange or place of exchange of the item.
             @param dataSnapshot a snapshot of the added child data
             @param s an optional argument, not used in this method
             */
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
                                    if (itemType != null && itemType.equals(userPreference)) {
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
                                if ( (itemType !=null && itemType.matches(pattern)) || (exchangeWith !=null && exchangeWith.matches(pattern)) || (location !=null &&location.matches(pattern))) {
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

    /**
     Checks if the given receiver email is the same as the given provider email.
     @param receiverEmail The email of the receiver.
     @param providerEmail The email of the provider.
     @return true if the given receiver email is the same as the given provider email, false otherwise.
     */
    boolean checkItemIsPostedByTheReceiver(String receiverEmail, String providerEmail) {
        return receiverEmail.equalsIgnoreCase(providerEmail);
    }
}