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


        rateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Switch to the page for rating the receiver
            }
        });

    }

    public void getLayoutComponents(){
        itemInformationView = (TextView)findViewById(R.id.productInformation);
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
        receiverEmail = intent.getStringExtra("receiverEmail");
        String a = "receiver id who purchased the product";
        String b = "the product received in exchange";
        String c = "the estimated price entered by the receiver";
        String d = "sold date";
    }

    public void setItemInformation(){
        //Put the item information to the itemInformationView
        String itemInfoString = "Item: " + itemName + "\nItem Type: " + itemType+ "\n\nDescription: "
                + itemDescription + "\n\nPreferred Exchange Item: " + preferredExchange;
        itemInformationView.setText(itemInfoString);
    }
}
