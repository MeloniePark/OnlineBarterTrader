package com.example.onlinebartertrader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * SoldItemActivity.java
 *
 * Description:
 *  Activity to display the details of a sold item and allow the provider to
 *  rate the receiver.
 *
 */
public class SoldItemActivity extends AppCompatActivity{

    // Intent data declaration
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

    String receiverRating;

    // Layouts declaration
    TextView itemInformationView;
    Button rateButton;

    /**
     * onCreate(...)
     * This method is called when the activity is starting.
     * It initializes the activity layout, gets layout components,
     * gets the intent item information and sets the item information.
     *
     * @param savedInstanceState a Bundle object containing the activity's previously saved state.
     */
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
            /**
             * Switches to the receiver's rating page
             * Sends the intent items to the ReceiverRating.class
             * @param v view
             */
            @Override
            public void onClick(View v){
                // Switch to the page for rating the receiver
                Intent intent = new Intent(SoldItemActivity.this,ReceiverRating.class);
                intent.putExtra("receiverEmail",receiverEmail);
                intent.putExtra("userEmailAddress",providerEmail.toLowerCase());
                intent.putExtra("itemKey",itemID);
                intent.putExtra("receiverRating",receiverRating);
                startActivity(intent);
            }
        });

    }

    /**
     * Initializes the layout components.
     */
    public void getLayoutComponents(){
        itemInformationView = (TextView)findViewById(R.id.productInformationProviderItem);
        rateButton = (Button)findViewById(R.id.buyNowButton);
    }

    /**
     * Gets item information from the intent.
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
        receiverEmail = intent.getStringExtra("receiverID");
        approxMarketValue = intent.getStringExtra("approxMarketValue");
        receiverEnteredPrice = intent.getStringExtra("receiverEnteredPrice");
        productReceived = intent.getStringExtra("productReceived");
        transactionDate = intent.getStringExtra("transactionDate");

        receiverRating  = intent.getStringExtra("receiverRating");;
    }

    /**
     * Sets and Displays the item information.
     */
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
