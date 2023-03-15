package com.example.onlinebartertrader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.util.NumberUtils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ProviderPostItemActivity extends AppCompatActivity implements View.OnClickListener {

    //firebase
    FirebaseDatabase database;
    DatabaseReference providerDBRef;
    DatabaseReference currentIDRef;

    //view for the lists
    String productType; //TODO: need to be drop down
    String description;
    String availableDate;
    String placeOfExchange;
    String approxMarketValue;
    String preferredExchangeInReturn;
    String currentStatus; //Available/Sold out.
    int currentID;
    Button providerPostBtn;
    String userEmailAddress = getIntent().getStringExtra("emailAddress");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_post_item);

        providerDBRef = database.getReference("Users/Provider/"+userEmailAddress);
        currentIDRef = database.getReference("itemID");
        currentIDRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentID = dataSnapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Failed to read value. " + databaseError.getCode());
            }
        });

        providerPostBtn = findViewById(R.id.providerSubmitPostProvider);
        providerPostBtn.setOnClickListener(this);
    }


    public boolean isProductTypeEmpty(String productType){
        if (productType.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean isProductTypeValid(String productType){
        String type1 = "furniture";
        String type2 = "clothes";
        String type3 = "computer accessories";
        String type4 = "mobile phones";
        String type5 = "baby toys";
        if (productType.equalsIgnoreCase(type1)||
                productType.equalsIgnoreCase(type2)||
                productType.equalsIgnoreCase(type3)||
                productType.equalsIgnoreCase(type4)||
                productType.equalsIgnoreCase(type5))   {
            return true;
        }
        return false;
    }

    public boolean isDescriptionEmpty(String description){
        if (description.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean isDateEmpty(String date){
        if (date.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean isDateValid(String date){
        Pattern pattern = Pattern.compile("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$");
        if (pattern.matcher(date).matches()) {
            return true;
        }
        return false;
    }

    public boolean isPlaceOfExchangeEmpty(String exchangePlace){
        if (exchangePlace.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean isApproxMarketValueEmpty(String marketValue){
        if (marketValue.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean isApproxMarketValueValid(String marketValue){
        Pattern pattern = Pattern.compile("[1-9][0-9]*");
        if (pattern.matcher(marketValue).matches()) {
            return true;
        }
        return false;
    }

    public boolean isPreferredExchangeInReturnEmpty(String preferredExchange){
        if (preferredExchange.isEmpty()) {
            return true;
        }
        return false;
    }

    // This method starts the ProviderLandingPage activity with the email address entered by the user as an extra
    protected void switch2ProviderLandingPage() {
        Intent intent = new Intent(this, ProviderLandingPage.class);
        startActivity(intent);
    }
    // This method sets the error message that will be displayed to the user
    // when an error occurs during login
    protected void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.errorMessageProviderProductAdd);
        statusLabel.setText(message.trim());
    }
    public int createNewIDThenIncrementRefID(){
        int newItemID = currentID + 1;
        currentIDRef.setValue(newItemID, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    System.out.println("itemID updated successfully.");
                } else {
                    System.out.println("Failed to update itemID. " + databaseError.getCode());
                }
            }
        });
        return newItemID;
    }


    // This method gets the value of the productType entered by the user
    protected String getProductType() {
        //TODO: change type
        EditText productType = findViewById(R.id.productTypeProviderPostItem);
        return productType.getText().toString().trim();
    }

    // This method gets the value of the description entered by the user
    protected String getDescription() {
        EditText description = findViewById(R.id.descriptionProviderPostItem);
        return description.getText().toString().trim();
    }

    // This method gets the value of the availableDate entered by the user
    protected String getAvailableDate() {
        EditText availableDate = findViewById(R.id.dateOfAvailabilityProviderPostItem);
        return availableDate.getText().toString().trim();
    }

    // This method gets the value of the placeOfExchange entered by the user
    protected String getPlaceOfExchange() {
        EditText placeOfExchange = findViewById(R.id.placeOfExchangeProviderPostItem);
        return placeOfExchange.getText().toString().trim();
    }
    // This method gets the value of the approxMarketValue entered by the user
    protected String getApproxMarketValue() {
        EditText approxMarketValue = findViewById(R.id.approximateMarketValueProviderPostItem);
        return approxMarketValue.getText().toString().trim();
    }
    // This method gets the value of the preferredExchangeInReturn entered by the user
    protected String getPreferredExchangeInReturn() {
        EditText preferredExchangeInReturn = findViewById(R.id.preferredExchangesInReturnProviderPostItem);
        return preferredExchangeInReturn.getText().toString().trim();
    }
    @Override
    public void onClick(View view) {
//        //where we move on to posting provider's goods page.
//        //Functionality will be added in future iteration
//        emailAddressEntered = getRidOfDot(getEmailAddressEntered());
//        passwordEntered = getPasswordEntered();
//        String errorMessage;
//
//        // Check if either the email or password is empty
//        if (isEmptyEmail(emailAddressEntered) || isEmptyPassword(passwordEntered)) {
//            errorMessage = getResources().getString(R.string.EMPTY_EMAIL_OR_PASSWORD).trim();
//            setStatusMessage(errorMessage);
//            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
//        }
//        // Checking if the entered email is a valid email format
//        else if (isValidEmailAddress(emailAddressEntered)) {
//            // Checking if the email is in the database
//            if (emailInDatabase()) {
//                // Check if the entered password matches the password in the database
//                if (checkPassword()) {
//                    // If the user is a provider, go to the provider landing page
//                    if (view.getId() == R.id.providerLoginButtonLogIn) {
//                        switch2ProviderLandingPage();
//                    }
//                }
//                // If the entered password is incorrect, display an error message
//                else {
//                    errorMessage = getResources().getString(R.string.INCORRECT_PASSWORD).trim();
//                    setStatusMessage(errorMessage);
//                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
//                }
//            }
//            // If the entered email is not in the database, display an error message
//            else {
//                errorMessage = getResources().getString(R.string.NOT_REGISTERED_EMAIL).trim();
//                setStatusMessage(errorMessage);
//                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
//            }
//        }
//        // If the entered email is not in the database, display an error message
//        else {
//            errorMessage = getResources().getString(R.string.NOT_REGISTERED_EMAIL).trim();
//            setStatusMessage(errorMessage);
//            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
//        }
//    }
    }
}
