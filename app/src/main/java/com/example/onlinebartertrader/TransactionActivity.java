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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class TransactionActivity extends AppCompatActivity implements View.OnClickListener {

    // Declaring class-level variables to be used in different methods
    FirebaseDatabase database = null;
    DatabaseReference userRefForCheckEmail;
    DatabaseReference userRefForCheckPassword;
    String emailAddressEntered;
    String passwordEntered;
    Button providerLoginButton;
    Button receiverLoginButton;
    ArrayList<String> emailsFound = new ArrayList<>();
    ArrayList<String> passwordFound =new ArrayList<>();
    volatile boolean dataRetrieved = false;
    boolean retrievedEmail = false, retrievedPassword=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_log_in);

    }

    // This method gets the value of the password entered by the user
    protected String getExchangeItemEntered() {
        EditText password = findViewById(R.id.passwordLogIn);
        return password.getText().toString().trim();
    }

    // This method gets the value of the email entered by the user
    protected String getValueEntered() {
        EditText emailAddress = findViewById(R.id.emailAddressLogIn);
        return emailAddress.getText().toString().trim();
    }

    // This method checks if the email address entered by the user is empty
    protected boolean isEmptyExchangeItem(String emailAddress) {
        return emailAddress.isEmpty();
    }

    // This method checks if the password entered by the user is empty
    protected boolean isEmptyValue(String password) {
        return password.isEmpty();
    }

    // This method checks if the email address entered by the user is valid using a regular expression
    protected boolean isValidValue(String emailAddress) {
        return emailAddress.matches("^[A-Za-z0-9.+_-]+@[A-Za-z0-9-]+.[a-zA-Z0-9.-]+$");
    }


    // This method starts the ReceiverLandingPage activity with the email address entered by the user as an extra
    protected void switch2ReceiverLandingPage() {
        Intent intent = new Intent(this, ReceiverLandingPage.class);
        intent.putExtra("emailAddress", emailAddressEntered.toLowerCase());
        startActivity(intent);
    }


    // This method sets the error message that will be displayed to the user
    // when an error occurs during login
    protected void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.errorMessageLogIn);
        statusLabel.setText(message.trim());
    }

    protected String getRidOfDot(String rawEmail) {
        return rawEmail.replace(".", "");
    }
    // This method is called when the user clicks the login button
    @Override
    public void onClick(View view) {
//        while (!dataRetrieved);
//
//        // Getting the email and password entered by the user
//        while (retrievedEmail ==false);
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
//                    // If the user is a receiver, go to the receiver landing page
//                    else if (view.getId() == R.id.receiverLoginButtonLogIn) {
//                        switch2ReceiverLandingPage();
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
    }
}
