package com.example.onlinebartertrader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        emailRef = database.getReference("templateUser/provider/userInfo");
        passwordRef = database.getReference("templateUser/provider/userInfo/email");
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

    protected void switch2ProviderLandingPage() {
        Intent intent = new Intent(this, ProviderLandingPage.class);
        intent.putExtra("emailAddress", emailAddress);
        startActivity(intent);
    }

    protected void switch2ReceiverLandingPage() {
        Intent intent = new Intent(this, ReceiverLandingPage.class);
        intent.putExtra("emailAddress", emailAddress);
        startActivity(intent);
    }

    protected boolean emailInDatabase(String emailAddress) {
        Boolean[] value = new Boolean[1];
        emailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    emailFromDatabase = (String) childSnapshot.child("email").getValue();
                    if (emailFromDatabase.equals(emailAddress)) {
                        value[0] = true;
                        break;
                    }
                    value[0] = false;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Error", "Failed to read value.", databaseError.toException());
            }
        });
        if (!value[0]) {
            Toast.makeText(getApplicationContext(), "Email Address is not registered.", Toast.LENGTH_LONG).show();
        }
        return true;
    }

    protected boolean checkPassword(String password) {
        Boolean[] value = new Boolean[1];
        passwordRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    passwordFromDatabase = (String) childSnapshot.child("email").getValue();
                    if (passwordFromDatabase.equals(password)) {
                        value[0] = true;
                        break;
                    }
                    value[0] = false;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Error", "Failed to read value.", databaseError.toException());
            }
        });
        return value[0];
    }

    @Override
    public void onClick(View view) {
        if (isEmptyEmail(emailAddress) || isEmptyPassword(password)){
            Toast.makeText(getApplicationContext(),"Either Email Address or Password is empty.", Toast.LENGTH_LONG).show();
        }
        else if (isValidEmailAddress(emailAddress)) {
            if (emailInDatabase(emailAddress)) {
                if (checkPassword(password)) {
                    if (view.getId() == R.id.providerLoginButton) {
                        switch2ProviderLandingPage();
                    } else if (view.getId() == R.id.receiverLoginButton) {
                        switch2ReceiverLandingPage();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Password is incorrect.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Enter Valid Email Address", Toast.LENGTH_LONG).show();
            }
        }
    }
}