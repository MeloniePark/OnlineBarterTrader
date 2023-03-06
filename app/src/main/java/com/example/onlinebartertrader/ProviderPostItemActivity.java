package com.example.onlinebartertrader;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.ArrayList;

public class ProviderPostItemActivity extends AppCompatActivity implements View.OnClickListener {

    //firebase
    FirebaseDatabase database;
    DatabaseReference providerDBRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
    }


    public boolean isProductTypeEmpty(String productType){
        return false;
    }

    public boolean isProductTypeValid(String productType){
        return false;
    }

    public boolean isDescriptionEmpty(String description){
        return false;
    }

    public boolean isDateEmpty(String date){
        return false;
    }

    public boolean isDateValid(String date){
        return false;
    }

    public boolean isPlaceOfExchangeEmpty(String exchangePlace){
        return false;
    }

    public boolean isApproxMarketValueEmpty(int marketValue){
        return false;
    }

    public boolean isApproxMarketValueValid(int marketValue){
        return false;
    }

    public boolean isPreferredExchangeInReturnEmpty(String preferredExchange){
        return false;
    }




    @Override
    public void onClick(View view) {
        //where we move on to posting provider's goods page.
        //Functionality will be added in future iteration 
    }
}
