package com.example.onlinebartertrader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseDatabase database = null;
    DatabaseReference emailRef;
    DatabaseReference passwordRef;
    String emailAddress;
    String password;
    Button providerLoginButton;
    Button receiverLoginButton;
    String emailFromDatabase;
    String passwordFromDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        providerLoginButton = findViewById(R.id.providerLoginButton);
        receiverLoginButton = findViewById(R.id.receiverLoginButton);
        providerLoginButton.setOnClickListener(this);
        receiverLoginButton.setOnClickListener(this);

        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");
        emailRef = database.getReference("Email Address");
        passwordRef = database.getReference("Password");
    }

    protected boolean isEmptyEmail(String emailAddress) {
        return emailAddress.isEmpty();
    }

    protected boolean isEmptyPassword(String password) {
        return password.isEmpty();
    }

    protected boolean isValidEmailAddress(String emailAddress) {
        return emailAddress.matches("^[A-Za-z0-9.+_-]+@[A-Za-z0-9-]+.[a-zA-Z0-9.-]+$");
    }

    protected boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.*[0-9]).{8,}$");
    }


    protected void switch2ProviderLandingPage() {
        Intent intent = new Intent(this, ProviderLandingPage.class);
        intent.putExtra("emailAddress", emailAddress);
        intent.putExtra("password", password);
        startActivity(intent);
    }

    protected void switch2ReceiverLandingPage() {
        Intent intent = new Intent(this, ReceiverLandingPage.class);
        intent.putExtra("emailAddress", emailAddress);
        intent.putExtra("password", password);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if(isEmptyEmail(emailAddress) || isEmptyPassword(password)){
            Toast.makeText(getApplicationContext(),"Either Email Address or Password is empty.", Toast.LENGTH_LONG).show();
        }
        else if (isValidEmailAddress(emailAddress) && isValidPassword(password)){
            emailRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    emailFromDatabase = dataSnapshot.getValue(String.class);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    String errorRead = error.getMessage();
                    System.out.println(errorRead);
                }
            });
            passwordRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    passwordFromDatabase = dataSnapshot.getValue(String.class);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    String errorRead = error.getMessage();
                    System.out.println(errorRead);
                }
            });
            if(emailAddress.equals(emailFromDatabase) && password.equals(passwordFromDatabase)){
                receiverLoginButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch2ReceiverLandingPage();
                    }
                });
                providerLoginButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch2ProviderLandingPage();
                    }
                });
            }
            else{
                Toast.makeText(getApplicationContext(),"Either Email Address or Password is Incorrect.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
