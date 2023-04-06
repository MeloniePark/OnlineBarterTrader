package com.example.onlinebartertrader;

/*
Reference: Tutorial 3
Will be updating this!!

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

    private void connectToFirebase(){
        FirebaseDatabase firebaseDB;
        //will get path or location of where the database is being hosted
        firebaseDB = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");
        //creating reference variable inside the databased called "message"
        firebaseDBRef = firebaseDB.getReference("message");
    }

    //this will write "Hello CSCI3130" into the key "message" of the database
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

    //swithcing to sign up page
    protected void switch2SignUpPage() {
        Intent signUpIntent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(signUpIntent);
    }

    //swithcing to sign in page.
    protected void switch2SignInPage() {
        Intent signInIntent = new Intent(MainActivity.this, LoginActivity.class);
        //Intent signInIntent = new Intent(MainActivity.this, SearchFunctionality.class);
        startActivity(signInIntent);
    }

    //OnClick function for catching button clicks.
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