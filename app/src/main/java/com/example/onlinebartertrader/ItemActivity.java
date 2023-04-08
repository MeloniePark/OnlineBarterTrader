package com.example.onlinebartertrader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This ItemActivity class is in charge of the item's page
 * Item's page has a information of the product & option to chat with provider or buy item.**/
public class ItemActivity extends AppCompatActivity {

    String itemID;
    String itemName;
    String itemType;
    String itemDescription;
    String preferredExchange;
    String providerEmail;
    String receiverEmail;

    // View components
    TextView itemInformationView;
    Button transaction;
    Button chatWithProvider;

    /**
     * This method initializes the view components.
     *
     * @param savedInstanceState save and restore the state of activity.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);


        getLayoutComponents();
        getIntentItemInfo();
        setItemInformation();


        //On Chat Button Click, switch to chat.
        chatWithProvider.setOnClickListener(new View.OnClickListener(){

            /**
             * Switch to ChatActivity and sends extra info with intent
             * @param v view
             */
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), ChatActivity.class);
                intent.putExtra("providerEmail", providerEmail);
                intent.putExtra("receiverEmail", receiverEmail);
                intent.putExtra("userType", "1"); //1 means chat opened from receiver side
                intent.putExtra("CHAT_COLLECTION", receiverEmail+"_"+providerEmail);
                startActivity(intent);
            }
        });

        //On Buy now Button Click, switch to buy now.. - this will be implemented in US2
        transaction.setOnClickListener(new View.OnClickListener(){
            /**
             * Switch to the transaction, and sends extra info with intent
             * @param v view
             */
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), TransactionActivity.class);
                intent.putExtra("providerEmail", providerEmail);
                intent.putExtra("receiverEmail", receiverEmail);
                intent.putExtra("itemID", itemID);
                startActivity(intent);
            }
        });


    }

    /**
     * This method initializes the view components.
     */
    public void getLayoutComponents(){
        itemInformationView = (TextView)findViewById(R.id.productInformation);
        transaction = (Button)findViewById(R.id.buyNowButton);
        chatWithProvider = (Button)findViewById(R.id.chatWithProviderButton);
    }

    /**
     * This method gets the item's information from the intent sent from ReceiverItemList class.
     */
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
    }

    /**
     * This method sets the item's information to the itemInformationView.
     */
    public void setItemInformation(){
        //Put the item information to the itemInformationView
        String itemInfoString = "Item: " + itemName + "\nItem Type: " + itemType+ "\n\nDescription: "
                + itemDescription + "\n\nPreferred Exchange Item: " + preferredExchange;
        itemInformationView.setText(itemInfoString);
    }
}
