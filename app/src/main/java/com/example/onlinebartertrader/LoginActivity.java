package com.example.onlinebartertrader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

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
    boolean retrievedEmail = false, retrievedPassword=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // Finding the login buttons in the layout and attach click listeners to them
        providerLoginButton = findViewById(R.id.providerLoginButtonLogIn);
        receiverLoginButton = findViewById(R.id.receiverLoginButtonLogIn);

        providerLoginButton.setOnClickListener(this);
        receiverLoginButton.setOnClickListener(this);

        // Initializing Firebase database and get references to email and password nodes
        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");
        userRefForCheckEmail = database.getReference("Users/Provider/");
        userRefForCheckPassword = database.getReference("Users/Provider/");

        userRefForCheckEmail.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userEmail = userSnapshot.getKey().replace(".", "");
                    emailsFound.add(userEmail);
                    passwordFound.add(userSnapshot.child("password").getValue(String.class));
                    if (emailsFound.size()!=0){
                        retrievedEmail = true;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Failed to read value. " + databaseError.getCode());
            }
        });
    }

    // This method gets the value of the password entered by the user
    protected String getPasswordEntered() {
        EditText password = findViewById(R.id.passwordLogIn);
        return password.getText().toString().trim();
    }

    // This method gets the value of the email entered by the user
    protected String getEmailAddressEntered() {
        EditText emailAddress = findViewById(R.id.emailAddressLogIn);
        return emailAddress.getText().toString().trim();
    }

    // This method checks if the email address entered by the user is empty
    protected boolean isEmptyEmail(String emailAddress) {
        return emailAddress.isEmpty();
    }

    // This method checks if the password entered by the user is empty
    protected boolean isEmptyPassword(String password) {
        return password.isEmpty();
    }

    // This method checks if the email address entered by the user is valid using a regular expression
    protected boolean isValidEmailAddress(String emailAddress) {
        return emailAddress.matches("^[A-Za-z0-9.+_-]+@[A-Za-z0-9-]+.[a-zA-Z0-9.-]+$");
    }

    // This method starts the ProviderLandingPage activity with the email address entered by the user as an extra
    protected void switch2ProviderLandingPage() {
        Intent intent = new Intent(this, ProviderLandingPage.class);
        intent.putExtra("emailAddress", emailAddressEntered);
        startActivity(intent);
    }

    // This method starts the ReceiverLandingPage activity with the email address entered by the user as an extra
    protected void switch2ReceiverLandingPage() {
        Intent intent = new Intent(this, ReceiverLandingPage.class);
        intent.putExtra("emailAddress", emailAddressEntered);
        startActivity(intent);
    }


    // This method checks if the email address entered by the user is in the database
    protected boolean emailInDatabase() {
        int emailIndex = -1;
        for(int index = 0; index< emailsFound.size(); index++){
            String currentEmail = emailsFound.get(index);
            if (currentEmail.equalsIgnoreCase(emailAddressEntered)){
                emailIndex = index;
                break;
            }
        }
        return emailIndex != -1;
    }

    // This method checks if the entered password matches the password in the database
    protected boolean checkPassword() {
        if (!emailInDatabase()){
            return false;
        }
        int emailIndex = -1;
        for(int index = 0; index< emailsFound.size(); index++){
            String currentEmail = emailsFound.get(index);
            if (currentEmail.equalsIgnoreCase(emailAddressEntered)){
                emailIndex = index;
                break;
            }
        }
        String correspondingPassword = passwordFound.get(emailIndex);
        return correspondingPassword.equals(passwordEntered);
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
        // Getting the email and password entered by the user
        while (retrievedEmail ==false);
        emailAddressEntered = getRidOfDot(getEmailAddressEntered());
        passwordEntered = getPasswordEntered();
        String errorMessage;
        System.out.println(emailsFound);
        System.out.println(passwordFound);

        // Check if either the email or password is empty
        if (isEmptyEmail(emailAddressEntered) || isEmptyPassword(passwordEntered)) {
            errorMessage = getResources().getString(R.string.EMPTY_EMAIL_OR_PASSWORD).trim();
            setStatusMessage(errorMessage);
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
        // Checking if the entered email is a valid email format
        else if (isValidEmailAddress(emailAddressEntered)) {
            // Checking if the email is in the database
            if (emailInDatabase()) {
                // Check if the entered password matches the password in the database
                if (checkPassword()) {
                    // If the user is a provider, go to the provider landing page
                    if (view.getId() == R.id.providerLoginButtonLogIn) {
                        switch2ProviderLandingPage();
                    }
                    // If the user is a receiver, go to the receiver landing page
                    else if (view.getId() == R.id.receiverLoginButtonLogIn) {
                        switch2ReceiverLandingPage();
                    }
                }
                // If the entered password is incorrect, display an error message
                else {
                    errorMessage = getResources().getString(R.string.INCORRECT_PASSWORD).trim();
                    setStatusMessage(errorMessage);
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                }
            }
            // If the entered email is not in the database, display an error message
            else {
                errorMessage = getResources().getString(R.string.NOT_REGISTERED_EMAIL).trim();
                setStatusMessage(errorMessage);
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
            }
        }
        // If the entered email is not in the database, display an error message
        else {
            errorMessage = getResources().getString(R.string.NOT_REGISTERED_EMAIL).trim();
            setStatusMessage(errorMessage);
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
    }
}
