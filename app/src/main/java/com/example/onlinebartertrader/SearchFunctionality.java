package com.example.onlinebartertrader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchFunctionality extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");
    DatabaseReference usersRef = database.getReference("Users/Provider/");
    DatabaseReference userRefForEmail;
    ArrayList<String> itemList = new ArrayList<>();
    String[] preferences = { "All", "Clothing", "Electronics", "Furniture", "General Goods"};
    String preference = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_functionality);

        // Get the intent that started this activity
        Intent intent = getIntent();

        // Retrieve the email address from the intent extra
        String emailAddress = intent.getStringExtra("emailAddress");
        userRefForEmail = database.getReference("Users/Receiver/"+emailAddress);
        DatabaseReference preferenceRef = userRefForEmail.child("preference");

        // Attach a ValueEventListener to preferenceRef
        preferenceRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    preference = snapshot.getValue(String.class);
                    ArrayList<String> preferenceList = new ArrayList<>(Arrays.asList(preferences));
                    preferenceList.remove(preference);
                    preferenceList.add(0, preference);
                    preferences = preferenceList.toArray(new String[preferenceList.size()]);

                    Spinner spin = findViewById(R.id.spinner);
                    spin.setOnItemSelectedListener(SearchFunctionality.this);
                    ArrayAdapter<String> aa = new ArrayAdapter<>(SearchFunctionality.this, android.R.layout.simple_spinner_item, preferences);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin.setAdapter(aa);
                } else {
                    System.out.println("Preference does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error retrieving preference: " + error.getMessage());
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner spinner = findViewById(R.id.spinner);
        String selectedValue = spinner.getSelectedItem().toString();
        DatabaseReference preferenceRef = userRefForEmail.child("preference");
        preferenceRef.setValue(selectedValue);
        System.out.println("Selected value: " + selectedValue);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}