package com.example.onlinebartertrader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SignUpActivity.java
 *
 * Description: SignUpActivity class is in charge of handling the Sign up activity.
 *              The user can sign in using the @dal.ca email as id, and password.
 *              The on page alert pops up when the id or password fail to satisfy the restriction.
 */

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase database = null;
    DatabaseReference receiverReference;
    DatabaseReference providerReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");
        receiverReference = database.getReference("Users/Receiver/");
        providerReference = database.getReference("Users/Provider/");

        //attaching the event handler
        Button signUp = findViewById(R.id.signUpButtonSignUp);
        signUp.setOnClickListener(this);
        Button cancel = findViewById(R.id.returnButtonSignUp);
        cancel.setOnClickListener(v -> switch2LandingPage());
    }

    /**
     * isEmptyEmail(String email) Checks if the email is not entered.
     * @param email String value taken from the user input
     * @return email.isEmpty() boolean value that if email is empty returns true, otherwise false.
     */
    protected boolean isEmptyEmail(String email) {
        return email.isEmpty();
    }

    /**
     * isEmptyPassword(String password) Checks if the password is not entered.
     * @param password String value taken from user input
     * @return password.isEmpty() is boolean value that if password is empty returns true,
     *          otherwise false.
     */
    protected boolean isEmptyPassword(String password) {
        return password.isEmpty();
    }

    // regular expression for email address
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");

    /**
     * isValidEmail(String email
     * @param email : Check if the email entered is in correct format
     * @return matcher.find() is boolean value that if the given string matches the EMAIL_PATTERN
     *                  returns true, otherwise false.
     */
    protected boolean isValidEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.find();
    }

    /**
     * isValidPassword(String password) : Check if the password entered is in correct format
     * @param password : String password input from the user
     * @return returns true if the all three password length, case, special character conditions
     *          are satisfied, otherwise returns false.
     */
    protected boolean isValidPassword(String password) {
        return checkPasswordLength(password) && checkPasswordCase(password) && checkPasswordSpecialChar(password);
    }

    /**
     * checkPasswordLength(String password) : checks password length
     * @param password : String password input from user.
     * @return  returns true if password length is more than or equal to 6, otherwise false.
     * Reference: learned from TDDDemo code by Usmi Mukherjee
     */
    protected boolean checkPasswordLength(String password) {
        return password.length() >= 6;
    }

    /**
     * checkPasswordCase(String password):
     *          password should be the combination of uppercase and lowercase
     * @param password
     * @return boolean value True if password is combination of lower and uppercase letters, false otherwise.
     * Reference: learned from TDDDemo code by Usmi Mukherjee
     */
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

    /**
     * checkPasswordSpecialChar(String password):
     *          Checks if password includes the special char expect for [a-zA-Z0-9]*
     * @param password
     * @return boolean value True if the password does not contain special char, otherwise false.
     */
    public boolean checkPasswordSpecialChar(String password) {
        String regex = "[a-zA-Z0-9]*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    /**
     * isSamePassword(String password, String passwordMatch):
     *              Validate the passwords entered twice match with each other
     * @param password  String password from user input
     * @param passwordMatch    string passwordMatch is user input of second password.
     * @return  boolean True if password and passwordMatch matches.
     */
    public boolean isSamePassword(String password, String passwordMatch) {
        return password.equalsIgnoreCase(passwordMatch);
    }

    /**
     * getPassword() : Retrieve the password entered by the user
     * @return String trimmed user entered password.
     */
    protected String getPassword() {
        EditText password = findViewById(R.id.passwordSignUp);
        return password.getText().toString().trim();
    }

    /**
     *  getPasswordMatch() : Retrieve the validating password entered by the user
     * @return String trimmed user entered second password.
     */
    protected String getPasswordMatch() {
        EditText passwordMatch = findViewById(R.id.passwordMatchSignUp);
        return passwordMatch.getText().toString().trim();
    }

    /**
     * getEmail() : Retrieve the email enterted by the user
     * @return String trimmed user entered email
     */
    protected String getEmail() {
        EditText email = findViewById(R.id.emailAddressSignUp);
        return email.getText().toString().trim();
    }

    /**
     * setStatusMessage(String message): Set error message
     * @param message  String of the error message.
     */
    protected void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.errorMessageSignup);
        statusLabel.setText(message.trim());
    }

    /**
     * switch2LandingPage() :
     *      switch the page to landing page if the user decides to cancel the current signup action
     */
    protected void switch2LandingPage() {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * switch2LogInPage() :
     *          switch the page to login page once sign up succeed
     */
    protected void switch2LogInPage() {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * store2Database(String email, String password):
     *      Stores hte user id (email) and password to the database.
     * @param email     User entered string email
     * @param password      User entered string password
     */
    protected void store2Database(String email, String password){
        receiverReference = receiverReference.child(email.replace(".", "").toLowerCase());
        receiverReference.child("preference").setValue("all");
        receiverReference.child("password").setValue(password);

        // Add user under "Provider" node
        providerReference = providerReference.child(email.replace(".", "").toLowerCase());
        providerReference.child("password").setValue(password);
    }

    /**
     * onClick(View v):
     *      This method is called when the user clicks the login button
     *      In charge of getting all password, email values and display error message
     *          in case of condition violation.
     * @param v
     */
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

        setStatusMessage(errorMessage);

        if (errorMessage.equals("")) {
            store2Database(emailAddress, password);
            switch2LogInPage();
        }
        else{
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
        }

    }
}
