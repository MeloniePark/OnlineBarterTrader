package com.example.onlinebartertrader;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This ItemActivity class is in charge of the item's page
 * Item's page has a information of the product & option to chat with provider or buy item.**/
public class ItemActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
    }
}
