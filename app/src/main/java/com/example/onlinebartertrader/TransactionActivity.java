package com.example.onlinebartertrader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;
/**
 * TransactionActivity.java
 *
 * Description:
 * This class is responsible for handling transactions between the provider and the receiver.
 * It uses the Firebase Realtime Database to update the item's current status, transaction date,
 * and other relevant details once the transaction is confirmed.
 *
 */
public class TransactionActivity extends AppCompatActivity implements View.OnClickListener {

    // Declaring class-level variables to be used in different methods
    FirebaseDatabase database = null;
    DatabaseReference itemRef;
    String passwordEntered;
    Button confirmButtun;
    ArrayList<String> emailsFound = new ArrayList<>();
    ArrayList<String> passwordFound =new ArrayList<>();
    volatile boolean dataRetrieved = false;
    boolean retrievedEmail = false;
    boolean retrievedPassword = false;

    String itemID;
    String itemExchangeEntered;
    String estValueEntered;
    String providerEmail;
    String receiverEmail;

    /**
     * Called when the activity is starting.
     * Initializes the activity with the layout and gets the intent information.
     * Initializes the Firebase database with the item reference using
     * the provider email and item ID passed from the intent.
     *
     * @param savedInstanceState the saved instance state bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        getIntentInfo();

        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");
        itemRef = database.getReference("Users/Provider/"+providerEmail+"/items/"+itemID);

        confirmButtun = findViewById(R.id.receiverTransactionConfirmBtn);
        confirmButtun.setOnClickListener(this);
    }

    /**
     * This method retrieves the information passed in from the intent that started this activity.
     * It gets the item ID, provider email, and receiver email.
     */
    public void getIntentInfo(){
        Intent intent = getIntent();
        itemID = intent.getStringExtra("itemID");
        providerEmail = intent.getStringExtra("providerEmail");
        receiverEmail = intent.getStringExtra("receiverEmail");
    }

    /**
     * This method retrieves the value of the item in exchange entered by the user.
     * @return The item in exchange entered by the user as a string.
     */
    protected String getExchangeItemEntered() {
        EditText exchangeItem = findViewById(R.id.receiverProductInExchange);
        return exchangeItem.getText().toString().trim();
    }

    /**
     * This method retrieves the estimated cost entered by the user.
     * @return The estimated cost entered by the user as a string.
     */
    protected String getValueEntered() {
        EditText estValue = findViewById(R.id.receiverTransactionEstCost);
        return estValue.getText().toString().trim();
    }

    /**
     * This method checks if the item in exchange entered by the user is empty.
     * @param emailAddress The item in exchange entered by the user.
     * @return True if the item in exchange entered by the user is empty, false otherwise.
     */
    protected boolean isEmptyExchangeItem(String emailAddress) {
        return emailAddress.isEmpty();
    }

    /**
     * This method checks if the estimated cost entered by the user is empty.
     * @param password The estimated cost entered by the user.
     * @return True if the estimated cost entered by the user is empty, false otherwise.
     */
    protected boolean isEmptyValue(String password) {
        return password.isEmpty();
    }

    /**
     * This method checks if the value address entered by the user is valid using a regular expression.
     * @param emailAddress the email address entered by the user
     * @return true if the email address is valid, false otherwise
     */
    protected boolean isValidValue(String emailAddress) {
        Pattern pattern = Pattern.compile("[1-9]\\d*");
        return (pattern.matcher(emailAddress).matches());
    }

    /**
     * This method changes the function to connect and switch to the rating page for the receiver.
     */
    // For Ahmed's US!! Change this to switch2RatingPage
    // Change this function to connect and switch to the rating page for receiver!
    // Functionality completed by: Qiaodan
    protected void switch2RatingPage() {
        Intent intent = new Intent(this, ProviderRating.class);
        intent.putExtra("receiverEmail", receiverEmail);
        intent.putExtra("providerEmail", providerEmail);
        intent.putExtra("itemID", itemID);
        startActivity(intent);
    }

    /**
     * This method change the values for the given item
     */
    protected void confirmTransaction() {

        Calendar currentCalendar = Calendar.getInstance();
        Date currentTime = currentCalendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(currentTime);

        itemRef.child("currentStatus").setValue("Sold Out");
        itemRef.child("receiverID").setValue(receiverEmail);
        itemRef.child("transactionDate").setValue(formattedDate);
        itemRef.child("productReceived").setValue(itemExchangeEntered);
        itemRef.child("receiverEnteredPrice").setValue(estValueEntered);
//        transactionDate, receiverID, productReceived, and receiverEnteredPrice
    }

    /**
     * This method sets the error message that will be displayed to the user
     * when an error occurs during confirming.
     * @param message the error message to be displayed to the user
     */
    protected void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.transactionReceiverErrorMessage);
        statusLabel.setText(message.trim());
    }

    /**
     * This method is called when the user clicks the Confirm button.
     * @param view the view that was clicked
     */
    @Override
    public void onClick(View view) {
        // Getting the item in exchange and estimated cost entered by the user
        itemExchangeEntered = getExchangeItemEntered();
        estValueEntered = getValueEntered();
        String errorMessage;

        // Check if either the item or value is empty
        if (isEmptyValue(estValueEntered)) {
            errorMessage = getResources().getString(R.string.EMPTY_RECEIVER_EST_ITEM_COST).trim();
            setStatusMessage(errorMessage);
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
        else
        if (isEmptyExchangeItem(itemExchangeEntered)) {
            errorMessage = getResources().getString(R.string.EMPTY_RECEIVER_EXCHANGE_PRODUCT).trim();
            setStatusMessage(errorMessage);
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
        // Checking if the entered value is numeric
        else if (isValidValue(estValueEntered)) {
            confirmTransaction();
            switch2RatingPage();
        }
        // If the value is not numeric, display an error message
        else {
            errorMessage = getResources().getString(R.string.INVALID_RECEIVER_EST_ITEM_COST).trim();
            setStatusMessage(errorMessage);
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
    }
}
