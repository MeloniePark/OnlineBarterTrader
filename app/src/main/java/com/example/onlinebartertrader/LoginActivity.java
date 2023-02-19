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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseDatabase database = null;
    DatabaseReference emailRef;
    DatabaseReference passwordRef;
    String emailAddressEntered;
    String passwordEntered;
    Button providerLoginButton;
    Button receiverLoginButton;
    String emailFromDatabase;
    String passwordFromDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        providerLoginButton = findViewById(R.id.providerLoginButtonLogIn);
        receiverLoginButton = findViewById(R.id.receiverLoginButtonLogIn);
        providerLoginButton.setOnClickListener(this);
        receiverLoginButton.setOnClickListener(this);

        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");
        emailRef = database.getReference("templateUser/provider/userInfo/email");
        passwordRef = database.getReference("templateUser/provider/userInfo/password");

        passwordRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                passwordFromDatabase = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Error", "Failed to read value.", databaseError.toException());
            }
        });

        emailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                emailFromDatabase = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Error", "Failed to read value.", databaseError.toException());
            }
        });


    }

    protected String getPasswordEntered() {
        EditText password = findViewById(R.id.passwordLogIn);
        return password.getText().toString().trim();
    }

    protected String getEmailAddressEntered() {
        EditText emailAddress = findViewById(R.id.emailAddressLogIn);
        return emailAddress.getText().toString().trim();
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
        intent.putExtra("emailAddress", emailAddressEntered);
        startActivity(intent);
    }

    protected void switch2ReceiverLandingPage() {
        Intent intent = new Intent(this, ReceiverLandingPage.class);
        intent.putExtra("emailAddress", emailAddressEntered);
        startActivity(intent);
    }

    protected boolean emailInDatabase() {
        return emailFromDatabase.equalsIgnoreCase(emailAddressEntered);
    }

    protected boolean checkPassword() {
        return passwordFromDatabase.equals(passwordEntered);
    }

    protected void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.errorMessageLogIn);
        statusLabel.setText(message.trim());
    }

    @Override
    public void onClick(View view) {
        emailAddressEntered = getEmailAddressEntered();
        passwordEntered = getPasswordEntered();
        String errorMessage;

        if (isEmptyEmail(emailAddressEntered) || isEmptyPassword(passwordEntered)) {
            errorMessage = getResources().getString(R.string.EMPTY_EMAIL_OR_PASSWORD).trim();
            setStatusMessage(errorMessage);
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
        else if (isValidEmailAddress(emailAddressEntered)) {
            if (emailInDatabase()) {
                if (checkPassword()) {
                    if (view.getId() == R.id.providerLoginButtonLogIn) {
                        switch2ProviderLandingPage();
                    } else if (view.getId() == R.id.receiverLoginButtonLogIn) {
                        switch2ReceiverLandingPage();
                    }
                }
                else {
                    errorMessage = getResources().getString(R.string.INCORRECT_PASSWORD).trim();
                    setStatusMessage(errorMessage);
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();

                }
            }
        }
        else {
            errorMessage = getResources().getString(R.string.NOT_REGISTERED_EMAIL).trim();
            setStatusMessage(errorMessage);
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
    }
}
