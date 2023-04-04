package com.example.onlinebartertrader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.*;


public class ChatActivity extends AppCompatActivity{

    public ChatActivity() {
        System.out.println("I am in Chat Activity");
    }

    String itemID;
    String itemName;
    String itemType;
    String itemDescription;
    String preferredExchange;
    String providerEmail;
    String receiverEmail;

    RecyclerView recyclerView;
    EditText chatWriteMessage;
    Button chatSendButton;





    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getLayoutComponents();
        getIntentInfo();

        //Now get the


    }

    public void getLayoutComponents(){
        recyclerView = findViewById(R.id.recyclerChatView);
        chatWriteMessage = findViewById(R.id.chatWriteMessage);
        chatSendButton = findViewById(R.id.chatSendButton);
    }

    public void getIntentInfo(){
        Intent intent = getIntent();
        itemID = intent.getStringExtra("itemID");
        itemName = intent.getStringExtra("itemName");
        itemType = intent.getStringExtra("itemType");
        itemDescription = intent.getStringExtra("description");
        preferredExchange = intent.getStringExtra("preferredExchange");
        providerEmail = intent.getStringExtra("providerEmail");
        receiverEmail = intent.getStringExtra("receiverEmail");
    }


    public boolean checkProvider(String provider, String user){
        return provider.equalsIgnoreCase(user);
    }

    public boolean checkReceiver(String receiver, String user){
        return receiver.equalsIgnoreCase(user);
    }

    public boolean isMessageEmpty(String message){
        return message.equals("");
    }





}
