package com.example.onlinebartertrader;

/**
 * MainActivity.java
 *
 * MainActivity.java is in charge of the main launch page of the Online Barter Trader application.
 * MainActivity has path to sign up or sign in button.
 * MainActivity also checks the proper connection to the firebase
 *
 * Reference: Tutorial 3
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //two firebase library that we will be using.
    private DatabaseReference firebaseDBRef;
    private TextView textView;  //for reading whatever is in this textelement.
    Button signUpButton;
    Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //register the views for landing page
        textView = findViewById(R.id.welcomeMessageMainPage);
        signUpButton = findViewById(R.id.signUpButtonMainPage);
        signInButton = findViewById(R.id.signInButtonMainPage);

        //setting onclicklistener -> on button click, the listener will run functionality inside it.
        signUpButton.setOnClickListener(this);
        signInButton.setOnClickListener(this);

        //connect to firebase
        connectToFirebase();
        writeToFirebase();
        listenToDataChanges();
    }

    /**
     * Conecting to the designated firebase for Online Barter Trader Application.
     * @param :  No parameter
     */
    private void connectToFirebase(){
        FirebaseDatabase firebaseDB;
        //will get path or location of where the database is being hosted
        firebaseDB = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");
        //creating reference variable inside the databased called "message"
        firebaseDBRef = firebaseDB.getReference("message");
    }

    /**
     * writeToFirebase checks if writing to firebase is working
     *  Writes "Barter Trader!" to the set path (key "message")
     * @param: no parameter
     */
    private void writeToFirebase(){
        firebaseDBRef.setValue("Barter Trader!");
    }

    //reading from the database
    private void listenToDataChanges(){
        firebaseDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String valueRead = snapshot.getValue(String.class);

                //at res/layout/activity_main.xml
                //modify the helloWorld attribute id.
                textView.setText("Welcome to " + valueRead);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //in case the textView fails
                final String errorRead = error.getMessage();
                textView.setText("Error: " + errorRead);
            }
        });
    }

    /**
     * Switches to the Sign Up Page
     */
    protected void switch2SignUpPage() {
        Intent signUpIntent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(signUpIntent);
    }

    /**
     * Switches to Sign in page
     */
    protected void switch2SignInPage() {
        Intent signInIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(signInIntent);
    }

    /**
     * Onclick function that react to the button clicks and switch to sign in or sign up
     *  according to the which button is clicked.
     * @param view  The view of the MainActivity landing page.
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.signInButtonMainPage){
            switch2SignInPage();
        }
        else if(view.getId() == R.id.signUpButtonMainPage){
            switch2SignUpPage();
        }
    }
}