package com.example.onlinebartertrader;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.util.NumberUtils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ProviderPostItemActivity extends AppCompatActivity implements View.OnClickListener {

    //firebase
    FirebaseDatabase database;
    DatabaseReference providerDBRef;


    //view for the lists
    ListView ProductType;
    ListView Description;
    ListView Date;
    ListView PlaceOfExchange;
    ListView ApproxMarketValue;
    ListView PreferredExchangeInReturn;
    Button providerPostBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
    }


    public boolean isProductTypeEmpty(String productType){
        if (productType.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean isProductTypeValid(String productType){
        String type1 = "furniture";
        String type2 = "clothes";
        String type3 = "computer accessories";
        String type4 = "mobile phones";
        String type5 = "baby toys";
        if (productType.equalsIgnoreCase(type1)||
                productType.equalsIgnoreCase(type2)||
                productType.equalsIgnoreCase(type3)||
                productType.equalsIgnoreCase(type4)||
                productType.equalsIgnoreCase(type5))   {
            return true;
        }
        return false;
    }

    public boolean isDescriptionEmpty(String description){
        if (description.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean isDateEmpty(String date){
        if (date.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean isDateValid(String date){
        Pattern pattern = Pattern.compile("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$");
        if (pattern.matcher(date).matches()) {
            return true;
        }
        return false;
    }

    public boolean isPlaceOfExchangeEmpty(String exchangePlace){
        if (exchangePlace.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean isApproxMarketValueEmpty(String marketValue){
        if (marketValue.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean isApproxMarketValueValid(String marketValue){
        Pattern pattern = Pattern.compile("[1-9][0-9]*");
        if (pattern.matcher(marketValue).matches()) {
            return true;
        }
        return false;
    }

    public boolean isPreferredExchangeInReturnEmpty(String preferredExchange){
        if (preferredExchange.isEmpty()) {
            return true;
        }
        return false;
    }




    @Override
    public void onClick(View view) {
        //where we move on to posting provider's goods page.
        //Functionality will be added in future iteration 
    }
}
