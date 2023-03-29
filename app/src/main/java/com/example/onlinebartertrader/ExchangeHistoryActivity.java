package com.example.onlinebartertrader;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExchangeHistoryActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ExchangeHistoryAdapter mAdapter;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchangehistory);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mRecyclerView = findViewById(R.id.exchange_history_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get the user role (Provider or Receiver) and user ID from the intent
        String userRole = getIntent().getStringExtra("userRole");
        String userId = getIntent().getStringExtra("userId");

        loadExchangeHistory(userRole, userId);
    }

    private void loadExchangeHistory(String userRole, String userId) {
        DatabaseReference userRef = mDatabase.child("Users").child(userRole).child(userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ExchangeItem> exchangeItems = new ArrayList<>();
                DataSnapshot itemsSnapshot = dataSnapshot.child("items");
                for (DataSnapshot itemSnapshot : itemsSnapshot.getChildren()) {
                    ExchangeItem exchangeItem = itemSnapshot.getValue(ExchangeItem.class);
                    exchangeItems.add(exchangeItem);
                }

                mAdapter = new ExchangeHistoryAdapter(ExchangeHistoryActivity.this, exchangeItems);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ExchangeHistoryActivity", "Error loading exchange history", databaseError.toException());
            }
        });
    }

    public class ExchangeItem {
        private String approxMarketValue;
        private String currentStatus;
        private String dateOfAvailability;
        private String description;
        private String placeOfExchange;
        private String preferredExchange;
        private String productName;
        private String productType;

        public ExchangeItem() {
        }


    }

