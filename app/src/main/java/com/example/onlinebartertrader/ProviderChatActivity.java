package com.example.onlinebartertrader;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * ProviderChatActivity.java
 *
 * Description:
 *      The ProviderChatActivity class is one of the supporting class for the chat functionality
 *      for the provider side.
 *      The class is in charge of listing all the posted items by the current provider, and
 *      put chat functionality on the item to allow provider to chat with receiver.
 */
public class ProviderChatActivity extends AppCompatActivity {


    ArrayList<String> chatUsers = new ArrayList<>();
    ListView userLists;
    protected FirebaseDatabase database;
    DatabaseReference chatDBRef;
    String providerEmail;
    protected static final String NOSUPPORT_2 = "No support for this operation in iteration 2";

    /**
     * method runs on creation of the page.
     * @param savedInstanceState needed for onCreate function.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");
        setContentView(R.layout.activity_provider_chat);

        Intent intent = getIntent();
        userLists = findViewById(R.id.usernameSelectProviderChat);
        providerEmail = intent.getStringExtra("providerEmail");
        setListeners();
    }

    /**
     * setListeners()
     * Listening to the data change.
     * Contains different methods including essential chat functionalities.
     */
    public void setListeners() {

        //array Adapter for the listview to list all the items of the provider.
        ArrayAdapter<String> userChatArrAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, chatUsers);
        userLists.setAdapter(userChatArrAdapter);
        chatDBRef = database.getReference("chat");
        chatDBRef.addChildEventListener(new ChildEventListener() {

            /**
             * @param dataSnapshot snapshot of data at current point
             * @param s firebase
             */
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @com.google.firebase.database.annotations.Nullable String s) {
                String receiverProvider = dataSnapshot.getKey();
                String[] parts = receiverProvider.split("_");
                String currentReceiver = parts[0].split("@")[0] + "@" + parts[0].split("@")[1];
                String currentProvider = parts[1].split("@")[0] + "@" + parts[1].split("@")[1];

                DatabaseReference receiverProviderRef = database.getReference("chat/"+receiverProvider);

                boolean sameUser = checkChatIsToProvider(currentProvider, providerEmail);

                //set item click listener
                userLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    /**
                     *
                     * @param adapterView : adapterview displaying the list.
                     * @param view view for index
                     * @param index index of the item
                     * @param l  long value
                     */
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                        String clickedChat = (String) adapterView.getItemAtPosition(index);
                        DatabaseReference chatsRef = database.getReference("chat");
                        chatsRef.addListenerForSingleValueEvent(new ValueEventListener() {

                            /**
                             * on Data's change the method runs.
                             *     It is sending the intent and datas to the ChatActivity.
                             *
                             * @param dataSnapshot  snapshot of data at current point
                             */
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot chatSnapshot : dataSnapshot.getChildren()) {
                                    String currentChatID = chatSnapshot.getKey();
                                    assert currentChatID != null;
                                    if (currentChatID.equalsIgnoreCase(clickedChat+"_"+providerEmail)) {
                                        String[] thisParts = receiverProvider.split("_");
                                        String thisReceiver = thisParts[0].split("@")[0] + "@" + thisParts[0].split("@")[1];
                                        Intent intent = new Intent(view.getContext(), ChatActivity.class);
                                        intent.putExtra("providerEmail", providerEmail);
                                        intent.putExtra("receiverEmail", thisReceiver);
                                        intent.putExtra("userType", "2"); //1 means chat opened from receiver side
                                        intent.putExtra("CHAT_COLLECTION", currentChatID);
                                        startActivity(intent);
                                    }
                                }
                            }

                            /**
                             * Not implemented, but left for future implementation.
                             * @param databaseError database error
                             */
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // This may be used for future implementation
                            }
                        });
                    }
                });


                if (sameUser) {
                    receiverProviderRef.addChildEventListener(new ChildEventListener() {
                        /**
                         * onChild Added this method gets triggered.
                         *  if current receiver is not part of current Chat users, then gets addded
                         *  to the chat user.
                         *  If not, then data set change is notified.
                         *
                         * @param snapshot snapshot of data at current point
                         * @param s firebase
                         */
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @com.google.firebase.database.annotations.Nullable String s) {
                            if (!chatUsers.contains(currentReceiver)) {
                                chatUsers.add(currentReceiver);
                            }
                            userChatArrAdapter.notifyDataSetChanged();

                        }

                        /**
                         * onChildChanged(...)
                         *    On the child data change, notifies the data changes.
                         * @param snapshot snapshot of data at current point
                         * @param s firebase
                         */
                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @com.google.firebase.database.annotations.Nullable String s) {
                            userChatArrAdapter.notifyDataSetChanged();

                        }

                        /**
                         *  Not used in this iteartion, left for possible future implementation requirements
                         * @param snapshot snapshot of data at current point
                         */
                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                            //this method was left empty for possible future implementation requirements
                            throw new UnsupportedOperationException(NOSUPPORT_2);

                        }

                        /**
                         *  Not used in this iteartion, left for possible future implementation requirements
                         * @param snapshot snapshot of data at current point
                         * @param s firebase
                         */
                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @com.google.firebase.database.annotations.Nullable String s) {
                            //this method was left empty for possible future implementation requirements
                            throw new UnsupportedOperationException(NOSUPPORT_2);

                        }

                        /**
                         *  Not used in this iteartion, left for possible future implementation requirements
                         * @param snapshot snapshot of data at current point
                         */
                        @Override
                        public void onCancelled(@NonNull DatabaseError snapshot) {
                            //this method was left empty for possible future implementation requirements
                            throw new UnsupportedOperationException(NOSUPPORT_2);

                        }

                    });
                }
            }

            /**
             *  onChildChanged(...)
             *      on the child changes, this method runs.
             *      This method is in control of multiple different activities method execution.
             *
             * @param dataSnapshot snapshot of data at current point
             * @param s firebase
             */
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @com.google.firebase.database.annotations.Nullable String s) {
                String receiverProvider = dataSnapshot.getKey();
                String[] parts = receiverProvider.split("_");
                String currentReceiver = parts[0].split("@")[0] + "@" + parts[0].split("@")[1];
                String currentProvider = parts[1].split("@")[0] + "@" + parts[1].split("@")[1];

                DatabaseReference receiverProviderRef = database.getReference("chat/"+receiverProvider);

                boolean sameUser = checkChatIsToProvider(currentProvider, providerEmail);

                //set item click listener
                userLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    /**
                     * onItemClick(...)
                     *  On the chat button clicked, the method runs.
                     *
                     * @param adapterView The adapterview displaying the list
                     * @param view  view of item
                     * @param index index of the item
                     * @param l long value
                     */
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                        String clickedChat = (String) adapterView.getItemAtPosition(index);
                        DatabaseReference chatsRef = database.getReference("chat");
                        chatsRef.addListenerForSingleValueEvent(new ValueEventListener() {

                            /**
                             * onDataChage(...)
                             *  on data change detected, this onDataChange method runs
                             *  This method sends the intent and extra data(providerEmail, receiverEmail,
                             *  userType, and currentChatID) to the chatActivity.class
                             *
                             * @param dataSnapshot snapshot of data at current point
                             */
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot chatSnapshot : dataSnapshot.getChildren()) {
                                    String currentChatID = chatSnapshot.getKey();
                                    assert currentChatID != null;
                                    if (currentChatID.equalsIgnoreCase(clickedChat+"_"+providerEmail)) {
                                        String[] thisParts = receiverProvider.split("_");
                                        String thisReceiver = thisParts[0].split("@")[0] + "@" + thisParts[0].split("@")[1];
                                        Intent intent = new Intent(view.getContext(), ChatActivity.class);
                                        intent.putExtra("providerEmail", providerEmail);
                                        intent.putExtra("receiverEmail", thisReceiver);
                                        intent.putExtra("userType", "2"); //1 means chat opened from receiver side
                                        intent.putExtra("CHAT_COLLECTION", currentChatID);
                                        startActivity(intent);
                                    }
                                }
                            }

                            /**
                             * Not used in this iteartion, left for possible future implementation requirements
                             * @param databaseError database Error
                             */
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // This is kept for future implementation.
                            }
                        });
                    }
                });


                if (sameUser) {
                    receiverProviderRef.addChildEventListener(new ChildEventListener() {

                        /**
                         * onChildAdded(...)
                         *  On the data added, if the current chat users include current receiver user,
                         *  then notifies the chat addition (data set changed)
                         *  Otherwise, add the current receiver user as the chat User.
                         *
                         * @param snapshot snapshot of data at current point
                         * @param s firebase
                         */
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @com.google.firebase.database.annotations.Nullable String s) {
                            if (!chatUsers.contains(currentReceiver)) {
                                chatUsers.add(currentReceiver);
                            }
                            userChatArrAdapter.notifyDataSetChanged();

                        }

                        /**
                         * onChildChanged(...)
                         * On the child data change, notifies the data changed.
                         *
                         * @param snapshot snapshot of data at current point
                         * @param s firebase
                         */
                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @com.google.firebase.database.annotations.Nullable String s) {
                            userChatArrAdapter.notifyDataSetChanged();

                        }

                        /**
                         * Not used in this iteartion, left for possible future implementation requirements
                         * @param snapshot snapshot of data at current point
                         */
                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                            //this method was left empty for possible future implementation requirements
                            throw new UnsupportedOperationException(NOSUPPORT_2);

                        }

                        /**
                         * Not used in this iteartion, left for possible future implementation requirements
                         * @param snapshot snapshot of data at current point
                         * @param s firebase
                         */
                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @com.google.firebase.database.annotations.Nullable String s) {
                            //this method was left empty for possible future implementation requirements
                            throw new UnsupportedOperationException(NOSUPPORT_2);

                        }

                        /**
                         * Not used in this iteartion, left for possible future implementation requirements
                         * @param snapshot snapshot of data at current point
                         */
                        @Override
                        public void onCancelled(@NonNull DatabaseError snapshot) {
                            //this method was left empty for possible future implementation requirements
                            throw new UnsupportedOperationException(NOSUPPORT_2);

                        }

                    });
                }
            }

            /**
             * Not used in this iteartion, left for possible future implementation requirements
             * @param dataSnapshot snapshot of data at current point
             */
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                //this method was left empty for possible future implementation requirements
                throw new UnsupportedOperationException(NOSUPPORT_2);
            }

            /**
             * Not used in this iteartion, left for possible future implementation requirements
             * @param dataSnapshot  snapshot of data at current point
             * @param s string from firebase
             */
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @com.google.firebase.database.annotations.Nullable String s) {
                //this method was left empty for possible future implementation requirements
                throw new UnsupportedOperationException(NOSUPPORT_2);
            }

            /**
             * Not used in this iteartion, left for possible future implementation requirements
             * @param databaseError database error
             */
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //this method was left empty for possible future implementation requirements
                throw new UnsupportedOperationException(NOSUPPORT_2);
            }
        });
    }

    /**
     * checkChatIsToProvider(String currentEmail, String providerEmail) :
     *  Checks if the current chat is to the current provider logged in.
     *
     * @param currentEmail the current session's email id
     * @param providerEmail provider's email id
     * @return  returns true if currentEmail is equal to the provider's email.
     */
    boolean checkChatIsToProvider(String currentEmail, String providerEmail) {
        return currentEmail.equalsIgnoreCase(providerEmail);
    }
}
