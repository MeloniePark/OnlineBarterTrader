package com.example.onlinebartertrader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.*;
/**
 The SearchActivity class represents the activity that allows users to search for items based on specific criteria.
 This class extends the AppCompatActivity class and implements the AdapterView.OnItemSelectedListener interface.
 The OnItemSelectedListener is used to handle user interactions with the dropdown menus that are used to select the search criteria.
 */
public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");
    DatabaseReference usersRef = database.getReference("Users/Provider/");
    DatabaseReference userRefForEmail;
    ArrayList<String> itemList = new ArrayList<>();
    String[] preferences = { "All","clothes","furniture", "computer accessories", "mobile phones", "baby toys"};
    String preference = "All";
    ListView receiverItemList;
    String emailAddress;
    String query = "null";
    //Logging
    Logger logger = Logger.getLogger(SearchActivity.class.getName());

    protected ArrayAdapter<String> receiverArrAdapter;
    protected ArrayList<String> receiverItems = new ArrayList<>();

    /**
     Initializes the activity, retrieves data from the previous activity, initializes UI components,
     sets up a listener for the search view and a spinner for filtering search results. It also creates
     an instance of the SearchedItemList class and starts listening to the database for changes to
     the search results based on user preferences and search queries.
     @param savedInstanceState saved data from the previous activity
     */
    @SuppressLint("MissingInflatedId")
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

        receiverItemList = (ListView) findViewById(R.id.receiverListSearch);
        SearchedItemList receiverList = new SearchedItemList(emailAddress, receiverItemList, this, query);
        receiverList.startListening();

        // Attach a ValueEventListener to preferenceRef
        preferenceRef.addListenerForSingleValueEvent(new ValueEventListener() {
            /**
             This method is called when the data at a location in the database has changed.
             It retrieves the user's preference from the database snapshot, updates the preferences array
             by removing the preference from its previous position and moving it to the top, and sets up
             the spinner with the updated preferences array.
             @param snapshot The snapshot of the database at the location where the change occurred.
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    preference = snapshot.getValue(String.class);
                    ArrayList<String> preferenceList = new ArrayList<>(Arrays.asList(preferences));
                    preferenceList.remove(preference);
                    preferenceList.add(0, preference);
                    preferences = preferenceList.toArray(new String[preferenceList.size()]);

                    Spinner spin = findViewById(R.id.spinnerSearch);
                    spin.setOnItemSelectedListener(SearchActivity.this);
                    ArrayAdapter<String> aa = new ArrayAdapter<>(SearchActivity.this, android.R.layout.simple_spinner_item, preferences);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin.setAdapter(aa);
                } else {
                    logger.info("Preference does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                logger.info("Error retrieving preference: " + error.getMessage());
            }
        });

        SearchView searchView = findViewById(R.id.searchViewSearch);

        searchView.setVisibility(View.VISIBLE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /**
             This method is called when the user submits a query in the search bar.
             It retrieves the searched text and starts listening to updates in the receiver list with the searched query.
             @param searchedText the text that the user has searched for
             @return true if the query has been submitted successfully, false otherwise
             */
            @Override
            public boolean onQueryTextSubmit(String searchedText) {
                query = searchedText;
                receiverItemList = (ListView) findViewById(R.id.receiverListSearch);
                SearchedItemList receiverList = new SearchedItemList(emailAddress, receiverItemList, SearchActivity.this, query);
                receiverList.startListening();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    /**
     Callback method to be invoked when an item in this view has been selected.
     @param adapterView The AdapterView where the selection happened.
     @param view The view within the AdapterView that was clicked.
     @param i The position of the view in the adapter.
     @param l The row id of the item that is selected.
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner spinner = findViewById(R.id.spinnerSearch);
        String selectedValue = spinner.getSelectedItem().toString();
        DatabaseReference preferenceRef = userRefForEmail.child("preference");
        preferenceRef.setValue(selectedValue);
        query = "null";
        receiverItemList = (ListView) findViewById(R.id.receiverListSearch);
        SearchedItemList receiverList = new SearchedItemList(emailAddress, receiverItemList, this, query);
        receiverList.startListening();
        System.out.println("Selected value: " + selectedValue);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //unused method, but left for possible future implementation
    }
}
