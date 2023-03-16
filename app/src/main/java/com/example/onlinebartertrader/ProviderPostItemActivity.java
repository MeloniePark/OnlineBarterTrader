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
import android.widget.Spinner;

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

/*
 ** ProviderPostItemActivity class
 ** User Story 2
 * ProviderPostItemActivity.java is linked to the activity_provider_post_item.xml.
 * This class is responsible for handling database related functionality
 *   and basic user input validation from the Product Post form in activity_provider_post_item.xml page.
 *
 */
public class ProviderPostItemActivity extends AppCompatActivity implements View.OnClickListener {

    //Firebase variables declared
    FirebaseDatabase database;
    DatabaseReference providerDBRef;
    DatabaseReference currentIDRef;

    //Post page's fields (text fields, button, or) variables
    String productType;         //Drop down product types menu
    String description;
    String availableDate;
    String placeOfExchange;
    String approxMarketValue;
    String preferredExchangeInReturn;
    String productName;
    String currentStatus;       //Available or Sold out.
    int currentID;              //unique product id digit
    Button providerPostBtn;     //Post button on product post page
    String userEmailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_post_item);

        //firebase linking & checking provider user's email address.
        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");
        userEmailAddress = getIntent().getStringExtra("emailAddress");

        //database itemID is used for storing unique digit id.
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

        //OnClick set up for submit post button (providerSubmitPostProvider) of the provider's post form.
        providerPostBtn = findViewById(R.id.providerSubmitPostProvider);
        providerPostBtn.setOnClickListener(this);

        //Drop Down List Creation for Product Type - Spinner used for creating drop-down list.
        Spinner spinnerForProductType = findViewById(R.id.productTypeMenuProviderPostItem);
        //Gets the productType lists resource array under values/array.xml.
        String[] productTypeList = getResources().getStringArray(R.array.productType);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                productTypeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerForProductType.setAdapter(adapter);
    }

    //This method checks if the product type field of the form is empty.
    public boolean isProductTypeEmpty(String productType){
        if (productType.isEmpty()) {
            return true;
        }
        return false;
    }

    //This method checks if the product type value is valid.
    //5 types are given: furniture, clothes, computer accessories, mobile phones and baby toys
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

    //This method checks if the description field of the form is empty.
    public boolean isDescriptionEmpty(String description){
        if (description.isEmpty()) {
            return true;
        }
        return false;
    }

    //This method checks if the product name field of the form is empty.
    public boolean isProductNameEmpty(String productName){
        if (productName.isEmpty()) {
            return true;
        }
        return false;
    }

    //This method checks if the date field of the form is empty.
    public boolean isDateEmpty(String date){
        if (date.isEmpty()) {
            return true;
        }
        return false;
    }

    //This method checks if the inputted date is valid - date should be later than current date.
    public boolean isDateValid(String date){
        Pattern pattern = Pattern.compile("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$");
        if (pattern.matcher(date).matches()) {
            return true;
        }
        return false;
    }

    //This method checks if the place of exchange field of the form is empty.
    public boolean isPlaceOfExchangeEmpty(String exchangePlace){
        if (exchangePlace.isEmpty()) {
            return true;
        }
        return false;
    }

    //This method checks if the approximate market value field of the form is empty.
    public boolean isApproxMarketValueEmpty(String marketValue){
        if (marketValue.isEmpty()) {
            return true;
        }
        return false;
    }

    //This method checks if the approximate market value's format is valid. - should be integer.
    public boolean isApproxMarketValueValid(String marketValue){
        Pattern pattern = Pattern.compile("[1-9][0-9]*");
        if (pattern.matcher(marketValue).matches()) {
            return true;
        }
        return false;
    }

    //This method checks if preferred exchange in return field of the form is empty.
    public boolean isPreferredExchangeInReturnEmpty(String preferredExchange){
        if (preferredExchange.isEmpty()) {
            return true;
        }
        return false;
    }


    // This method starts the ProviderLandingPage activity with the email address entered by the user as an extra
    protected void switch2ProviderLandingPage() {
        Intent intent = new Intent(this, ProviderLandingPage.class);
        intent.putExtra("emailAddress", userEmailAddress);
        startActivity(intent);
    }

    // This method sets the error message that will be displayed to the user
    // when an error occurs during login
    protected void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.errorMessageProviderProductAdd);
        statusLabel.setText(message.trim());
    }

    // This method gets the unique product id digit from the database & increment that digit
    //  for next product to have unique id (digit)
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
        Spinner productType = findViewById(R.id.productTypeMenuProviderPostItem);
        String selectedOption = productType.getSelectedItem().toString();
        return selectedOption;
    }

    //This method gets the value of the product name entered by the user.
    protected String getProductName() {
        EditText description = findViewById(R.id.productNameProviderPostItem);
        return description.getText().toString().trim();
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
        String errorMessage;
        productType = getProductType();
        description = getDescription();
        productName = getProductName();
        availableDate = getAvailableDate();
        placeOfExchange = getPlaceOfExchange();
        approxMarketValue = getApproxMarketValue();
        preferredExchangeInReturn = getPreferredExchangeInReturn();
        currentStatus = "Available"; //Available or Sold out. -Set to available by default when first posted

        // Error message displaying -> displays error message if required fields are empty
        // All the fields are required except description.
        if (isProductNameEmpty(productName)) {
            errorMessage = getResources().getString(R.string.EMPTY_PRODUCT_NAME).trim();
            setStatusMessage(errorMessage);
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
        else if (isProductTypeEmpty(productType)) {
            errorMessage = getResources().getString(R.string.EMPTY_PRODUCT_TYPE).trim();
            setStatusMessage(errorMessage);
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
        else if (isDateEmpty(availableDate)){
            errorMessage = getResources().getString(R.string.EMPTY_DATE_OF_AVAILABILITY).trim();
            setStatusMessage(errorMessage);
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
        else if (isPlaceOfExchangeEmpty(placeOfExchange)){
            errorMessage = getResources().getString(R.string.EMPTY_PLACE_OF_EXCHANGE).trim();
            setStatusMessage(errorMessage);
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
        else if (isApproxMarketValueEmpty(approxMarketValue)){
            errorMessage = getResources().getString(R.string.EMPTY_APPROXIMATE_MARKET_VALUE).trim();
            setStatusMessage(errorMessage);
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
        else if (isPreferredExchangeInReturnEmpty(preferredExchangeInReturn)){
            errorMessage = getResources().getString(R.string.EMPTY_PREFERRED_EXCHANGE_TYPE).trim();
            setStatusMessage(errorMessage);
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
        else if (!isProductTypeValid(productType)){
            errorMessage = getResources().getString(R.string.INVALID_PRODUCT_TYPE).trim();
            setStatusMessage(errorMessage);
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
        else if (!isProductTypeValid(productType)){
            errorMessage = getResources().getString(R.string.INVALID_PRODUCT_TYPE).trim();
            setStatusMessage(errorMessage);
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
        else if (!isApproxMarketValueValid(approxMarketValue)){
            errorMessage = getResources().getString(R.string.INVALID_APPROXIMATE_MARKET_VALUE).trim();
            setStatusMessage(errorMessage);
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
        else if (!isDateValid(availableDate)){
            errorMessage = getResources().getString(R.string.INVALID_DATE_OF_AVAILABILITY).trim();
            setStatusMessage(errorMessage);
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
        else {
            // newID gets the unique digit id from the createNewIDThenIncrementRefID().
            int newID = createNewIDThenIncrementRefID();

            //Data reference path for saving the product added.
            // path: database.getReference("Users/Provider/"+userEmailAddress+"/items/");
            String itemRef = "Users/Provider/" + userEmailAddress + "/items/" + newID;

            //Adds all the user provided fields in the form to the database.
            providerDBRef = database.getReference(itemRef);
            providerDBRef.child("productType").setValue(productType);
            providerDBRef.child("description").setValue(description);
            providerDBRef.child("productName").setValue(productName);
            providerDBRef.child("dateOfAvailability").setValue(availableDate);
            providerDBRef.child("placeOfExchange").setValue(placeOfExchange);
            providerDBRef.child("approxMarketValue").setValue(approxMarketValue);
            providerDBRef.child("preferredExchange").setValue(preferredExchangeInReturn);
            providerDBRef.child("currentStatus").setValue(currentStatus);
            switch2ProviderLandingPage();
        }
    }
}
