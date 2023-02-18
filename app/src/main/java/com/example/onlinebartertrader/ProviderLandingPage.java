package com.example.onlinebartertrader;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

public class ProviderLandingPage extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase database;
    DatabaseReference providerDBRef;
    ListView providerItemLists;

    Button providerPostBtn;

    ArrayList<String> providerItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

        //array Adapter for the listview to list all the items of the provider.
        final ArrayAdapter<String> providerArrAdapter = new ArrayAdapter<String>
                (ProviderLandingPage.this, android.R.layout.simple_list_item_1, providerItems);

        //
        providerItemLists = (ListView) findViewById(R.id.providerList);
        providerItemLists.setAdapter(providerArrAdapter);

        providerPostBtn = findViewById(R.id.providerPost);
        providerPostBtn.setOnClickListener(this);

        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");
        //creating reference variable inside the databased called "templateUser"
        providerDBRef = database.getReference("templateUser").child("provider").child("goods");

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


    @Override
    public void onClick(View view) {
        //where we move on to posting provider's goods page.
    }
}
