package com.example.onlinebartertrader;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProviderLandingPage extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase database;
    DatabaseReference providerDBRef;
    TextView textView;

    Button providerPostBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

        textView = findViewById(R.id.providerLanding);
        providerPostBtn = findViewById(R.id.providerPost);
        providerPostBtn.setOnClickListener(this);

        //database
        connectToProviderDB();
        writeToProviderDB();
        listenToProviderDB();


    }

    private void connectToProviderDB(){
        //will get path or location of where the database is being hosted
        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");
        //creating reference variable inside the databased called "templateUser"
        providerDBRef = database.getReference("templateUser").child("provider").child("goods");
    }

    //this will write into the key "templateUser" of the database
    private void writeToProviderDB(){
        providerDBRef.setValue("good2");
    }

    //reading from the database
    private void listenToProviderDB(){
        providerDBRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String valueRead = snapshot.getValue(String.class);

                //at res/layout/activity_provider.xml
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


    @Override
    public void onClick(View view) {

    }
}
