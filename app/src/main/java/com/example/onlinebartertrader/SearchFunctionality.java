package com.example.onlinebartertrader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Arrays;

public class SearchFunctionality extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");
    DatabaseReference usersRef = database.getReference("Users/Provider/");
    DatabaseReference userRefForEmail;
    ArrayList<String> itemList = new ArrayList<>();
    String[] preferences = { "All", "computer accessories", "Electronics", "Furniture", "baby toys"};
    String preference = "All";
    ListView receiverItemList;
    String emailAddress;
    String query = "null";
    protected ArrayAdapter<String> receiverArrAdapter;
    protected ArrayList<String> receiverItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_functionality);

        // Get the intent that started this activity
        Intent intent = getIntent();

        // Retrieve the email addresss from the intent extra
        emailAddress = intent.getStringExtra("emailAddress");
        if (emailAddress==null){
            emailAddress = "test@dalca";
        }
        userRefForEmail = database.getReference("Users/Receiver/"+emailAddress);
        DatabaseReference preferenceRef = userRefForEmail.child("preference");

        receiverItemList = (ListView) findViewById(R.id.receiverListReceiver);
        System.out.println(receiverItemList);
        ReceiverItemList receiverList = new ReceiverItemList(emailAddress, receiverItemList, this, query);
        receiverList.startListening();
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

                    Spinner spin = findViewById(R.id.spinnerSearch);
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

        SearchView searchView = findViewById(R.id.searchView);
        System.out.println(searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String searchedText) {
                query = searchedText;
                receiverItemList = (ListView) findViewById(R.id.receiverListReceiver);
                System.out.println(receiverItemList);
                ReceiverItemList receiverList = new ReceiverItemList(emailAddress, receiverItemList, SearchFunctionality.this, query);
                receiverList.startListening();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //receiverArrAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner spinner = findViewById(R.id.spinnerSearch);
        String selectedValue = spinner.getSelectedItem().toString();
        DatabaseReference preferenceRef = userRefForEmail.child("preference");
        preferenceRef.setValue(selectedValue);
        query = "null";
        receiverItemList = (ListView) findViewById(R.id.receiverListReceiver);
        System.out.println(receiverItemList);
        ReceiverItemList receiverList = new ReceiverItemList(emailAddress, receiverItemList, this, query);
        receiverList.startListening();
        System.out.println("Selected value: " + selectedValue);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
