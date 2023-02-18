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
    static ReceiverLandingPage receiverLandingPage;

    FirebaseDatabase database;
    DatabaseReference receiverDBRefAvailable;
    DatabaseReference receiverDBRefHistory;
    ListView receiverLists;

    Button receiverTradedBtn;
    Button receiverAvailableBtn;

    ArrayList<String> receiverItems = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

        receiverAvailableBtn = findViewById(R.id.availableProducts);
        receiverAvailableBtn.setOnClickListener(this);

        receiverTradedBtn = findViewById(R.id.tradedHistory);
        receiverTradedBtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        //array Adapter for the listview to list all the items of the provider.
        final ArrayAdapter<String> receiverArrAdapter = new ArrayAdapter<String>
                (ReceiverLandingPage.this, android.R.layout.simple_list_item_1, receiverItems);

        //
        receiverLists = (ListView) findViewById(R.id.receiverList);
        receiverLists.setAdapter(receiverArrAdapter);


        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");
        //creating reference variable inside the databased called "templateUser"



        //if availableProduct button is clicked -> show available products in that area.
        if (view.getId() == R.id.availableProducts){
            receiverDBRefAvailable = database.getReference("tradeArea").child("Halifax").child("goods");
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
        if (view.getId() == R.id.tradedHistory){
            receiverDBRefHistory = database.getReference("templateUser").child("receiver").child("receivedItem");
            receiverDBRefHistory.addChildEventListener(new ChildEventListener() {
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
    }
}
