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

/**
 * ChatActivity.java
 *
 * Description: ChatActivity class implements the instant chat functionality for the receiver
 *      and provider.
 *      Receiver and Provider can chat to each other in real time and the chat history is saved
 *      in the database for users to go back to their chat and read their previous conversations
 *
 * Reference: Instant Chat Tutorial
 */
public class ChatActivity extends AppCompatActivity{

    /**
     * ChatActivity() :
     *  Empty constructor used to check by printing message if the system is in ChatActivity.
     *  Plus firebase requires constructor.
     */
    public ChatActivity() {
        //Checking purpose
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


    /**
     * onCreate(Bundle savedInstanceState) :
     *      Runs when the page gets created.
     *      In charge of calling other methods to get layout components, intents data and other
     *      functionalities.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getLayoutComponents();
        getIntentInfo();
        getChatCollection();
        setListeners();
        getChatMessages();

    }

    /**
     * getLayoutComponents() : Getting layout components.
     */
    public void getLayoutComponents(){
        recyclerView = findViewById(R.id.recyclerChatView);
        chatWriteMessage = findViewById(R.id.chatWriteMessage);
        chatSendButton = findViewById(R.id.chatSendButton);
    }

    /**
     * getIntentInfo() :
     *      Getting intent data that got sent from the page linked previously.
     *      Plus, it checks the userInfo (receiver or provider)
     */
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

    /**
     * checkProvider(String provider, String user) :
     *      Checks if the user is provider.
     *
     * @param provider
     * @param user
     * @return  returns boolean True if user is provider, if not False.
     */
    public boolean checkProvider(String provider, String user){
        return provider.equalsIgnoreCase(user);
    }

    /**
     * checkReceiver(String receiver, String user) :
     *      Checks if the user is receiver.
     *
     * @param receiver
     * @param user
     * @return  boolean True if user is receiver, if not False.
     */
    public boolean checkReceiver(String receiver, String user){
        return receiver.equalsIgnoreCase(user);
    }

    /**
     * isMessageEmpty(String message) :
     *      Checks if the message inputted is empty or not.
     *
     * @param message
     * @return  boolean true if the messgae is empty, false if not.
     */
    public boolean isMessageEmpty(String message){
        return message.equals("");
    }

    /**
     * getChatCollection():
     *      Getter for the chat collection
     */
    private void getChatCollection() {
        chatCollection = getIntent().getStringExtra(CHAT_COLLECTION);
        if  (chatCollection == null) {
            chatCollection = "test@dal_hello@dalca";//set default value
        }
    }

    /**
     * setListeners():
     *      Listens to the chat send button click.
     */
    private void setListeners() {
        chatSendButton.setOnClickListener(view -> sendMessage());
    }

    /**
     * getChatMessages():
     *      Getter for the chat message.
     *      This getter gets the chats from the database and displays it on the recyclerView.
     */
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

    /**
     * sendMessage()
     *      Sends the message & stores the chat to database according to the user type.
     */
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

    /**
     * onStart():
     *      Start listening to the chat.
     */
    @Override
    protected void onStart() {
        super.onStart();
        chatAdapter.startListening();
    }

    /**
     * onStop():
     *      Stops listening to the chat.
     */
    @Override
    protected void onStop() {
        super.onStop();
        chatAdapter.stopListening();
    }

}
