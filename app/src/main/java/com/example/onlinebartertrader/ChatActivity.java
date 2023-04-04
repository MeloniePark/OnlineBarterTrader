package com.example.onlinebartertrader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
public class ChatActivity extends AppCompatActivity{

    public ChatActivity() {
        //This method is used to check by printing message if the system is in ChatActivity.
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


    public static final String CHAT_COLLECTION = "CHAT_COLLECTION";
    ChatAdapter chatAdapter;
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
        //userType = Integer.parseInt(intent.getStringExtra("userType"));

        if (intent.getStringExtra("userType") != null){
            try{
                userType = Integer.parseInt(intent.getStringExtra("userType"));
            } catch (NumberFormatException e){
                //deal with the userType being null
                userType = 0;
            }
        }

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
        if  (chatCollection == null) {
            chatCollection = "test@dal_hello@dalca";//set default value
        }
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
