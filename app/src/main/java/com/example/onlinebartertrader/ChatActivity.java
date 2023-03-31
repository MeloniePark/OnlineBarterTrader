package com.example.onlinebartertrader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
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
    int userType; //1 for receiver, 2 for provider

    RecyclerView recyclerView;
    EditText chatWriteMessage;
    Button chatSendButton;
//    UserSession myUserSession;


    public static final String CHAT_COLLECTION = "CHAT_COLLECTION";
//    private RecyclerView chatRecyclerView;
    ChatAdapter chatAdapter;
//    private EditText chatMessageET;
//    private Button chatSendBtn;
    private String chatCollection;



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getLayoutComponents();
        getIntentInfo();

        //Now get the
        getChatCollection();
        setListeners();
        System.out.println(chatCollection);
        getChatMessages();

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
        userType = Integer.parseInt(intent.getStringExtra("userType"));

        if (userType == 1) {
            UserSession.getInstance().setUser(receiverEmail);
        }
        else {
            UserSession.getInstance().setUser(providerEmail);
        }
    }

// mostly adapted from CSCI 3130 tutorial
    public boolean checkProvider(String provider, String user){
        return provider.equalsIgnoreCase(user);
    }

    public boolean checkReceiver(String receiver, String user){
        return receiver.equalsIgnoreCase(user);
    }

    public boolean isMessageEmpty(String message){
        return message.equals("");
    }

    private void getChatCollection() {
        chatCollection = getIntent().getStringExtra(CHAT_COLLECTION);
    }

    private void setListeners() {
        chatSendButton.setOnClickListener(view -> sendMessage());
    }

    private void getChatMessages() {
//        getting the chat messages
        final FirebaseRecyclerOptions<Chat> options = new FirebaseRecyclerOptions.Builder<Chat>()
                .setQuery(FirebaseDatabase.getInstance(FirebaseConstants.FIREBASE_URL)
                        .getReference().child("chat")
                        .child(chatCollection), Chat.class)
                .build();
//        getting chat adapter object and then bind the recycler view to the adapter
        chatAdapter = new ChatAdapter(options);
        recyclerView.setAdapter(chatAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void sendMessage() {
        final String chatMessage = chatWriteMessage.getText().toString();
        final Chat chatMessageObj;
        if (userType == 1){
            chatMessageObj = new Chat(receiverEmail, chatMessage);
        }
        else {
            chatMessageObj = new Chat(providerEmail, chatMessage);
        }
//storing the message and the username in the chat collection in database
        FirebaseDatabase.getInstance(FirebaseConstants.FIREBASE_URL)
                .getReference().child("chat")
                .child(chatCollection)
                .push()
                .setValue(chatMessageObj)
                .addOnSuccessListener(unused -> chatWriteMessage.setText(""))
                .addOnFailureListener(e ->
                        Toast.makeText(ChatActivity.this,
                                        getString(R.string.failed_to_send_message),
                                        Toast.LENGTH_SHORT)
                                .show());
    }

    @Override
    protected void onStart() {
        super.onStart();
        chatAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        chatAdapter.stopListening();
    }




}
