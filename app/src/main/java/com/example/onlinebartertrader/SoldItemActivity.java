package com.example.onlinebartertrader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SoldItemActivity extends AppCompatActivity {

    String itemID;
    String itemName;
    String itemType;
    String itemDescription;
    String preferredExchange;
    String providerEmail;
    String receiverEmail;
    String approxMarketValue;
    String receiverEnteredPrice;
    String productReceived;
    String transactionDate;

    TextView itemInformationView;
    Button rateButton;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sold_item);


        getLayoutComponents();
        getIntentItemInfo();
        setItemInformation();

        rateButton = findViewById(R.id.rateTheReceiverButtonProviderItem);
        rateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // TODO:Switch to the page for rating the receiver
            }
        });

    }

    public void getLayoutComponents(){
        itemInformationView = (TextView)findViewById(R.id.productInformationProviderItem);
        rateButton = (Button)findViewById(R.id.buyNowButton);
    }

    public void getIntentItemInfo(){
        Intent intent = getIntent();
        // Get item info from the intent sent from ReceiverItemList class
        itemID = intent.getStringExtra("itemID");
        itemName = intent.getStringExtra("itemName");
        itemType = intent.getStringExtra("itemType");
        itemDescription = intent.getStringExtra("description");
        preferredExchange = intent.getStringExtra("preferredExchange");
        providerEmail = intent.getStringExtra("providerEmail");
        receiverEmail = intent.getStringExtra("receiverID");
        approxMarketValue = intent.getStringExtra("approxMarketValue");
        receiverEnteredPrice = intent.getStringExtra("receiverEnteredPrice");
        productReceived = intent.getStringExtra("productReceived");
        transactionDate = intent.getStringExtra("transactionDate");
    }

    public void setItemInformation(){
        //Put the item information to the itemInformationView
        String itemInfoString =
                "Item ID: " + itemID
                + "\nItem Name: " + itemName
                + "\nItem Type: " + itemType
                + "\n\nPreferred Exchange Item: " + preferredExchange
                + "\nExchange Actually Got: " + productReceived
                + "\nApproximate Market Value: " + approxMarketValue
                + "\nValue of Item Reveied: " + receiverEnteredPrice
                + "\nReceiver ID: " + receiverEmail
                + "\nTransaction Date: " + transactionDate;
        itemInformationView.setText(itemInfoString);
    }
}
