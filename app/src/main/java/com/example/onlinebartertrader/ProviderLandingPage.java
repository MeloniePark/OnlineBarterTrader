package com.example.onlinebartertrader;

import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProviderLandingPage extends AppCompatActivity implements View.OnClickListener {

    //firebase
    FirebaseDatabase database;
    DatabaseReference providerDBRef;

    //view for the lists
    ListView providerItemLists;

    Button providerPostBtn;
    // initialize the button
    Button displayProviderItemsButton = findViewById(R.id.displayProviderItemsButton);

    //arraylists for listview
    ArrayList<String> providerItems = new ArrayList<>();
    //array Adapter for the listview to list all the items of the provider.
    final ArrayAdapter<String> providerArrAdapter = new ArrayAdapter<String>
            (ProviderLandingPage.this, android.R.layout.simple_list_item_1, providerItems);


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);


        //register the views, buttons and other components for the provider landing page.
        providerItemLists = (ListView) findViewById(R.id.providerListProvider);
        //setting array Adapter for the ListView providerItemLists.
        providerItemLists.setAdapter(providerArrAdapter);
        providerPostBtn = findViewById(R.id.providerPostProvider);

        //listens for click of the post button
        providerPostBtn.setOnClickListener(this);

        //Firebase Connection
        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");
        //creating reference variable inside the database called "Users"
        providerDBRef = database.getReference("Users").child("Provider");


        //Firebase data addition, modification, deletion, reading performed through this section.
        providerDBRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String valueRead = snapshot.getValue(String.class);
                providerItems.add(valueRead);
                providerArrAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                providerArrAdapter.notifyDataSetChanged();
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




    displayProviderItemsButton.OnClickListener(new View.OnClickListener()
        @Override
        public void onClick (View view){
        //where we move on to posting provider's goods page.
        //Functionality will be added in future iteration

        // clear the arraylist before adding items
        providerItems.clear();
        // get the data from firebase and add it to the arraylist
        providerDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    String item = dataSnapshot.getValue(String.class);
                    providerItems.add(item);
                }
                // update the adapter with the new data
                providerArrAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
