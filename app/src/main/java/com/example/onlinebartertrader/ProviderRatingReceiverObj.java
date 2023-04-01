package com.example.onlinebartertrader;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProviderRatingReceiverObj {
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");
    DatabaseReference receiverRatingDBRef;
    private String providerEmail;
    private int avgRating;
    private int totalRating;
    private int numRating;
    private int currRating;


    public ProviderRatingReceiverObj(String providerEmail,String itemId){
        this.providerEmail = providerEmail;
        receiverRatingDBRef = database.getReference("Users/Provider/items").child(itemId);
    }

    public void getDBInfo(){
        receiverRatingDBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                avgRating = Integer.parseInt(snapshot.child("productReceiverAvgRating").getValue().toString());
                totalRating = Integer.parseInt(snapshot.child("productReceiverTotalRating").getValue().toString());
                numRating = Integer.parseInt(snapshot.child("productReceiverTotalRatingNum").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addNewRating(int currRating){
        this.currRating = currRating;
        totalRating += currRating;
        numRating++;
        avgRating = (Integer) (totalRating/numRating);
        updateDB();
    }

    public int getAvgRating(){
        return avgRating;
    }

    private void updateDB(){
        receiverRatingDBRef.child("productReceiverAvgRating").setValue(Integer.toString(avgRating))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // The value was successfully updated
                        System.out.println("avg updated successfully!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle any errors here
                        System.out.println("Error updating value: " + e.getMessage());
                    }
                });
        receiverRatingDBRef.child("productReceiverTotalRating").setValue(Integer.toString(totalRating))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // The value was successfully updated
                        System.out.println("total updated successfully!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle any errors here
                        System.out.println("Error updating value: " + e.getMessage());
                    }
                });
        receiverRatingDBRef.child("productReceiverTotalRatingNum").setValue(Integer.toString(numRating))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // The value was successfully updated
                        System.out.println("total num updated successfully!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle any errors here
                        System.out.println("Error updating value: " + e.getMessage());
                    }
                });
    }

}
