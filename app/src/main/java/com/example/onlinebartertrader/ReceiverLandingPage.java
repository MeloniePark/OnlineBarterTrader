package com.example.onlinebartertrader;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ReceiverLandingPage extends AppCompatActivity implements View.OnClickListener{

    //Firebase
    FirebaseDatabase database;
    DatabaseReference receiverDBRefAvailable;
    DatabaseReference receiverDBRefHistory;

    //view for the lists
    ListView receiverLists;
    ListView receiverTradeList;

    Button receiverTradedBtn;
    Button receiverAvailableBtn;
    String userEmailAddress;

    //arraylists for listviews
    ArrayList<String> receiverItems = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

        userEmailAddress = getIntent().getStringExtra("emailAddress");
        //registering the button for the receiver's landing page
        //onclick listener listens set for each button click
        receiverAvailableBtn = findViewById(R.id.availableProductsReceiver);
        receiverAvailableBtn.setOnClickListener(this);

        receiverTradedBtn = findViewById(R.id.tradedHistoryReceiver);
        receiverTradedBtn.setOnClickListener(this);

        // US6 new functionality: alert the receiver when there is a new item added
        // and is interested by the user
        Alert itemAlert = new Alert(userEmailAddress, ReceiverLandingPage.this);
        itemAlert.startListening();
    }

    @Override
    public void onClick(View view) {

        //array Adapter for the listview to list all the items of the provider.
        final ArrayAdapter<String> receiverArrAdapter = new ArrayAdapter<String>
                (ReceiverLandingPage.this, android.R.layout.simple_list_item_1, receiverItems);

        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");

        //if availableProduct button is clicked -> show available products in that area.
        if (view.getId() == R.id.availableProductsReceiver){
            //register the views, buttons and other components for the receiver landing page.
            receiverLists = (ListView) findViewById(R.id.receiverListReceiver);
            receiverLists.setAdapter(receiverArrAdapter);

            //creating reference variable inside the databased called "templateUser"
            receiverDBRefAvailable = database.getReference("tradeArea").child("Halifax").child("goods");

            //Firebase data addition, modification, deletion, reading performed through this section.
            receiverDBRefAvailable.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    String valueRead = snapshot.getValue(String.class);
                    receiverItems.add(valueRead);
                    receiverArrAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    receiverArrAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        //if traded items button is clicked -> show traded items history of receiver
        if (view.getId() == R.id.tradedHistoryReceiver){
            //register the views, buttons and other components for the receiver landing page.
            receiverTradeList = (ListView) findViewById(R.id.receiverTraded);
            receiverTradeList.setAdapter(receiverArrAdapter);

            receiverDBRefHistory = database.getReference("templateUser").child("receiver").child("receivedItem");

            //Firebase data addition, modification, deletion, reading performed through this section.
            receiverDBRefHistory.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    String keyRead = snapshot.getKey();
                    receiverItems.add(keyRead);
                    receiverArrAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    receiverArrAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}
