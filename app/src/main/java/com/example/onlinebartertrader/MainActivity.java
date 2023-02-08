package com.example.onlinebartertrader;

/*
Reference: Tutorial 3
Will update the details of reference later.
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    //two firebase library that we will be using.
    private FirebaseDatabase firebaseDB;
    private DatabaseReference firebaseDBRef;
    private TextView textView;  //for reading whatever is in this textelement.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        //connect to firebase
        connectToFirebase();
        writeToFirebase();
        listenToDataChanges();
    }

    private void connectToFirebase(){
        //will get path or location of where the database is being hosted
        firebaseDB = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");
        //creating reference variable inside the databased called "message"
        firebaseDBRef = firebaseDB.getReference("message");
    }

    //this will write "Hello CSCI3130" into the key "message" of the database
    private void writeToFirebase(){
        firebaseDBRef.setValue("Hello it is connected!");
    }

    //reading from the database
    private void listenToDataChanges(){
        firebaseDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String valueRead = snapshot.getValue(String.class);

                //at res/layout/activity_main.xml
                //modify the helloWorld attribute id.
                textView.setText("Success: " + valueRead);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //in case the textView fails
                final String errorRead = error.getMessage();
                textView.setText("Error: " + errorRead);
            }
        });
    }

}