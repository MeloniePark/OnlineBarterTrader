package com.example.onlinebartertrader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
//    EditText password;
//    EditText passwordMatch;
//    EditText email;
//    TextView statusLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

//        password = findViewById(R.id.passwordSignUp);
//        passwordMatch = findViewById(R.id.passwordMatchSignUp);
//        email = findViewById(R.id.emailAddressSignUp);
//        statusLabel = findViewById(R.id.errorMessageSignup);

        //attaching the event handler
        Button signUp = findViewById(R.id.signUpButtonSignUp);
        signUp.setOnClickListener(this);
        Button cancel = findViewById(R.id.returnButtonSignUp);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {switch2LandingPage();}
        });
    }

    protected boolean isEmptyEmail(String email) {
        return email.isEmpty();
    }
    protected boolean isEmptyPassword(String password) {
        return password.isEmpty();
    }

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");


    protected boolean isValidEmail(String email) {
            Matcher matcher = EMAIL_PATTERN.matcher(email);
            return matcher.find();
    }

    protected boolean isValidPassword(String password) {
        if (checkPasswordLength(password) && checkPasswordCase(password) && checkPasswordSpecialChar(password)){
            return true;
        }
        else {
            return false;
        }
    }
    //learn from TDDDemo code by Usmi Mukherjee
    //password can not longer than 8
    protected boolean checkPasswordLength(String password){
        if (password.length() >= 8){
            return false;
        }else {
            return true;
        }
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
            } else if(Character.isLowerCase(currentChar)) {
                isLowerCase = true;
            }
        }
        if(isUpperCase && isLowerCase)
            return true;
        else
            return false;
    }

    //learn from TDDDemo code by Usmi Mukherjee
    //password should not include the special char expect for [a-zA-Z0-9_@$!]*
    public boolean checkPasswordSpecialChar(String password) {
        String regex = "[a-zA-Z0-9_@$!]*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public boolean isSamePassword(String password, String passwordMatch) {
        if (password.equalsIgnoreCase(passwordMatch)) {
            return true;
        }
        return false;
    }

    protected String getPassword() {
        EditText password = findViewById(R.id.passwordSignUp);
        return password.getText().toString().trim();
    }

    protected String getPasswordMatch() {
        EditText passwordMatch = findViewById(R.id.passwordMatchSignUp);
        return passwordMatch.getText().toString().trim();
    }

    protected String getEmail() {
        EditText email = findViewById(R.id.emailAddressSignUp);
        return email.getText().toString().trim();
    }

    protected void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.errorMessageSignup);
        statusLabel.setText(message.trim());
    }


    //switch the page
    protected void switch2LandingPage() {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
    }

    //switch the page
    protected void switch2LogInPage() {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void onClick(View v) {
        String password = getPassword();
        String emailAddress = getEmail();
        String passwordMatch =getPasswordMatch();
        String errorMessage = "";


        if (isEmptyEmail(emailAddress)) {
            errorMessage = getResources().getString(R.string.EMPTY_EMAIL_ADDRESS).trim();
        }

        else if (isEmptyPassword(password)||isEmptyPassword(passwordMatch)) {
            errorMessage = getResources().getString(R.string.EMPTY_PASSWORD).trim();
        }

        else if (!isValidEmail(emailAddress)) {
            errorMessage = getResources().getString(R.string.INVALID_EMAIL_ADDRESS).trim();
            setStatusMessage(errorMessage);
        }

        else if (!isValidPassword(password)) {
            errorMessage = getResources().getString(R.string.INVALID_PASSWORD).trim();
        }

        else if (!isSamePassword(password,passwordMatch)){
            errorMessage = getResources().getString(R.string.SAME_PASSWORD).trim();
        }

        setStatusMessage(errorMessage);

        if (errorMessage.equals("")){
            switch2LogInPage();
        }

    }
}
