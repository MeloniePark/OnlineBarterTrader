package com.example.onlinebartertrader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StatsPageActivity extends AppCompatActivity {

    private String userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        userRole = getIntent().getStringExtra("userRole");

        Button exchangeHistoryButton = findViewById(R.id.exchangeHistoryButton);
        exchangeHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatsPageActivity.this, ExchangeHistoryActivity.class);
                intent.putExtra("userRole", userRole);
                startActivity(intent);
            }
        });
    }
}

