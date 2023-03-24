package com.example.onlinebartertrader;

import android.content.Intent;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.BeforeClass;

import java.util.logging.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.ArrayList;

public class ProviderRatingObj {
    private String providerEmail;
    private int avgRating;
    private int currRating;
    private ArrayList<Integer> ratingList = new ArrayList<>();


    public ProviderRatingObj(){
        this.providerEmail = providerEmail;
    }

    public ProviderRatingObj(int currRating){
        this.providerEmail = providerEmail;
        this.currRating = currRating;
        ratingList.add(currRating);
        updateAvg();
    }

    public void addNewRating(int currRating){
        this.currRating = currRating;
        ratingList.add(currRating);
        updateAvg();
    }

    public int getAvgRating(){
        return avgRating;
    }

    private void updateAvg(){
        int totalRating = 0;
        for (int rating:ratingList) {
            totalRating += rating;
        }

        avgRating = totalRating/ratingList.size();
    }

}
