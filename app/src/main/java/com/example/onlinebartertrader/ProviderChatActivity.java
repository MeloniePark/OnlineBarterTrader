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

public class ProviderChatActivity extends AppCompatActivity {


    ArrayList<String> chatUsers = new ArrayList<>();
    ListView userLists;
    protected FirebaseDatabase database;
    DatabaseReference chatDBRef;
    String providerEmail;
    protected static final String NOSUPPORT_2 = "No support for this operation in iteration 2";

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


    public void setListeners() {

        //array Adapter for the listview to list all the items of the provider.
        ArrayAdapter<String> userChatArrAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, chatUsers);
        userLists.setAdapter(userChatArrAdapter);
        chatDBRef = database.getReference("chat");
        chatDBRef.addChildEventListener(new ChildEventListener() {
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
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                        String clickedChat = (String) adapterView.getItemAtPosition(index);
                        DatabaseReference chatsRef = database.getReference("chat");
                        chatsRef.addListenerForSingleValueEvent(new ValueEventListener() {
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

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // This may be used for future implementation
                            }
                        });
                    }
                });


                if (sameUser) {
                    receiverProviderRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @com.google.firebase.database.annotations.Nullable String s) {
                            if (!chatUsers.contains(currentReceiver)) {
                                chatUsers.add(currentReceiver);
                            }
                            userChatArrAdapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @com.google.firebase.database.annotations.Nullable String s) {
                            userChatArrAdapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                            //this method was left empty for possible future implementation requirements
                            throw new UnsupportedOperationException(NOSUPPORT_2);

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @com.google.firebase.database.annotations.Nullable String s) {
                            //this method was left empty for possible future implementation requirements
                            throw new UnsupportedOperationException(NOSUPPORT_2);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError snapshot) {
                            //this method was left empty for possible future implementation requirements
                            throw new UnsupportedOperationException(NOSUPPORT_2);

                        }

                    });
                }
            }

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
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                        String clickedChat = (String) adapterView.getItemAtPosition(index);
                        DatabaseReference chatsRef = database.getReference("chat");
                        chatsRef.addListenerForSingleValueEvent(new ValueEventListener() {
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

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // This is kept for future implementation.
                            }
                        });
                    }
                });


                if (sameUser) {
                    receiverProviderRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @com.google.firebase.database.annotations.Nullable String s) {
                            if (!chatUsers.contains(currentReceiver)) {
                                chatUsers.add(currentReceiver);
                            }
                            userChatArrAdapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @com.google.firebase.database.annotations.Nullable String s) {
                            userChatArrAdapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                            //this method was left empty for possible future implementation requirements
                            throw new UnsupportedOperationException(NOSUPPORT_2);

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @com.google.firebase.database.annotations.Nullable String s) {
                            //this method was left empty for possible future implementation requirements
                            throw new UnsupportedOperationException(NOSUPPORT_2);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError snapshot) {
                            //this method was left empty for possible future implementation requirements
                            throw new UnsupportedOperationException(NOSUPPORT_2);

                        }

                    });
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                //this method was left empty for possible future implementation requirements
                throw new UnsupportedOperationException(NOSUPPORT_2);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @com.google.firebase.database.annotations.Nullable String s) {
                //this method was left empty for possible future implementation requirements
                throw new UnsupportedOperationException(NOSUPPORT_2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //this method was left empty for possible future implementation requirements
                throw new UnsupportedOperationException(NOSUPPORT_2);
            }

        });
    }

    boolean checkChatIsToProvider(String currentEmail, String providerEmail) {
        return currentEmail.equalsIgnoreCase(providerEmail);
    }
}
