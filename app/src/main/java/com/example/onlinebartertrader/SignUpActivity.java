package com.example.onlinebartertrader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //attaching the event handler
        Button signUp = findViewById(R.id.signUpButtonSignUp);
        signUp.setOnClickListener(this);
        Button cancel = findViewById(R.id.returnButtonSignUp);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch2LandingPage();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    // Check if the email is not entered
    protected boolean isEmptyEmail(String email) {
        return email.isEmpty();
    }

    // Check if the password is not entered
    protected boolean isEmptyPassword(String password) {
        return password.isEmpty();
    }

    // regular expression for email address
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");

    // Check if the email entered is in correct format
    protected boolean isValidEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.find();
    }

    // Check if the password entered is in correct format
    protected boolean isValidPassword(String password) {
        return checkPasswordLength(password) && checkPasswordCase(password) && checkPasswordSpecialChar(password);
    }

    //learn from TDDDemo code by Usmi Mukherjee
    protected boolean checkPasswordLength(String password) {
        return password.length() >= 6;
    }

    //learn from TDDDemo code by Usmi Mukherjee
    //password should be the combination of uppercase and lowercase
    public boolean checkPasswordCase(String password) {
        boolean isUpperCase = false;
        boolean isLowerCase = false;
        char currentChar;
        for (int i = 0; i < password.length(); i++) {
            currentChar = password.charAt(i);
            if (Character.isUpperCase(currentChar)) {
                isUpperCase = true;
            } else if (Character.isLowerCase(currentChar)) {
                isLowerCase = true;
            }
        }
        return isUpperCase && isLowerCase;
    }

    //learn from TDDDemo code by Usmi Mukherjee
    //password should not include the special char expect for [a-zA-Z0-9]*
    public class User{


        public String email;
        public String password;
        public User(){}
        public User(String email, String password){
            this.email = email;
            this.password = password;
        }

    }

    public boolean checkPasswordSpecialChar(String password) {
        String regex = "[a-zA-Z0-9]*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    // Validate the passwords entered twice match with each other
    public boolean isSamePassword(String password, String passwordMatch) {
        return password.equalsIgnoreCase(passwordMatch);
    }

    // Retrieve the password entered by the user
    protected String getPassword() {
        EditText password = findViewById(R.id.passwordSignUp);
        return password.getText().toString().trim();
    }

    // Retrieve the validating password entered by the user
    protected String getPasswordMatch() {
        EditText passwordMatch = findViewById(R.id.passwordMatchSignUp);
        return passwordMatch.getText().toString().trim();
    }

    // Retrieve the email enterted by the user
    protected String getEmail() {
        EditText email = findViewById(R.id.emailAddressSignUp);
        return email.getText().toString().trim();
    }

    // Set error message
    protected void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.errorMessageSignup);
        statusLabel.setText(message.trim());
    }

    //switch the page to landing page if the user decides to cancel the current signup action
    protected void switch2LandingPage() {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
    }

    //switch the page to login page once sign up succeed
    protected void switch2LogInPage() {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    // This method is called when the user clicks the login button
    public void onClick(View v) {
        String password = getPassword();
        String emailAddress = getEmail();
        String passwordMatch = getPasswordMatch();
        String errorMessage = "";

        // Validating the email and password.
        // Allow sign up only if both are in correct format.
        if (isEmptyEmail(emailAddress)) {
            errorMessage = getResources().getString(R.string.EMPTY_EMAIL_ADDRESS).trim();
        } else if (isEmptyPassword(password) || isEmptyPassword(passwordMatch)) {
            errorMessage = getResources().getString(R.string.EMPTY_PASSWORD).trim();
        } else if (!isValidEmail(emailAddress)) {
            errorMessage = getResources().getString(R.string.INVALID_EMAIL_ADDRESS).trim();
            setStatusMessage(errorMessage);
        } else if (!isValidPassword(password)) {
            errorMessage = getResources().getString(R.string.INVALID_PASSWORD).trim();
        } else if (!isSamePassword(password, passwordMatch)) {
            errorMessage = getResources().getString(R.string.SAME_PASSWORD).trim();
        }
        else {
            mAuth.createUserWithEmailAndPassword(emailAddress, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();

                        String email = user.getEmail();

                        User newUser = new User(email, password);

                        mDatabase.child("users").child(email).setValue(newUser)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    switch2LandingPage();
                                } else {
                                    Toast.makeText(SignUpActivity.this,
                                            "Failed to create user account",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else {
                        Toast.makeText(SignUpActivity.this,
                                "Failed to create user account",
                                Toast.LENGTH_SHORT).show();
                    }


            }   });
        }
        setStatusMessage(errorMessage);


        if (errorMessage.equals("")) {
            switch2LogInPage();
        }
        else{
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
        }

    }
}
