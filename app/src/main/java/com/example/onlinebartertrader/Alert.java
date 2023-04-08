package com.example.onlinebartertrader;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.*;

/**
 * US 6: Alert
 * This Alert class is responsible for the User Story 6 Alert functionality
 *      with following acceptance criteria:
 *      AT1: As a user, when I log in as a receiver, if I have saved a preference
 *      through the search feature, then I should be notified through the app
 *      when a product matching the saved preference is available for sale.
 *
 *      AT2: As a user, when I log in as a receiver, if I have not saved a preference
 *      through the search feature, then I should not be notified about product
 *      availability, since I did not set the preference
 *
 */

public class Alert {
    //firebase linking & references variables initialization
    protected FirebaseDatabase database;
    protected DatabaseReference receiverDBRef;
    DatabaseReference providerDBRef;

    //variables initialization
    protected String userEmailAddress;
    protected String itemInterested = "nullType";
    protected Context myContext;

    //logger - logging is better exercise than system printing out.
    private static final Logger logger = Logger.getLogger(Alert.class.getName());

    /**
     * Creates a default new Alert instance.
     */
    public Alert() {
    }

    /**
     * Creates a new Alert instance.
     * @param userEmail The email address of the user to listen for data changes.
     * @param myContext The context in which the Alert instance is being used.
     */
    public Alert(String userEmail, Context myContext) {
        this.myContext = myContext;
        if (userEmail == null){
            userEmail = "us6espresso@dalca";
        }
        this.userEmailAddress = userEmail.toLowerCase(Locale.ROOT);

        //Links the database & get reference to the path we want to listen to data change or read value from
        database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");
        String itemRef = "Users/Receiver/" + userEmailAddress + "/preference/";
        receiverDBRef = database.getReference(itemRef);

        receiverDBRef.addValueEventListener(new ValueEventListener() {
            //on data change, gets the
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                itemInterested = dataSnapshot.getValue(String.class);
                logger.info("itemInterested"+itemInterested);
            }

            //log the database error code when fail to read from database
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                logger.info("Failed to read value. " + databaseError.getCode());
            }
        });

    }

    /**
     * Starts listening for data changes in the Firebase Realtime Database and logs any updates.
     */
    public void startListening() {

        //  The receiver side listens to the provider's item addition to notify receiver about item addition
        //  that matches their preference.
        //listens to the data (item addition) in provider side (created reference to provider's DB)
        providerDBRef = database.getReference("Users/Provider");
        providerDBRef.addChildEventListener(new ChildEventListener() {
            /**
             * This method is called when a child node is added to the reference.
             * @param dataSnapshot An immutable snapshot of the data at the specified database reference after the child has been added.
             * @param s The key of the previous child node, or null if it is the first child node.
             */
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String providerEmail = dataSnapshot.getKey();

                //database reference to where the provider's items are added
                DatabaseReference providerItemsRef = database.getReference("Users/Provider/" + providerEmail + "/items");
                if (providerEmail!= null && !providerEmail.equalsIgnoreCase(userEmailAddress)){
                    providerItemsRef.addChildEventListener(new ChildEventListener() {

                        //This method runs when the items gets added by the provider:
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            String itemType = dataSnapshot.child("productType").getValue(String.class);
                            String itemAvailDateString = dataSnapshot.child("dateOfAvailability").getValue(String.class);
                            String itemName = dataSnapshot.child("productName").getValue(String.class);
                            String currentStatus = dataSnapshot.child("currentStatus").getValue(String.class);
                            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

                            //If item type, item available date, item name is fetched from the database:
                            if (currentStatus != null && itemType != null && itemAvailDateString != null && itemName != null){
                                Date itemAvailDate = new Date();
                                //Sends the notification (+ logging) to the user about newly added item.
                                try {
//                                logger.info("date is "+itemAvailDateString);
                                    itemAvailDate = format.parse(itemAvailDateString);
                                    if (currentStatus.equalsIgnoreCase("Available") && isItemAvailable(itemAvailDate) && isUserInterested(itemType)) {
                                        sendNotification(itemType, itemName);
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        //This method runs when the items gets modified by the provider:
                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            String itemType = dataSnapshot.child("productType").getValue(String.class);
                            String itemAvailDateString = dataSnapshot.child("dateOfAvailability").getValue(String.class);
                            String itemName = dataSnapshot.child("productName").getValue(String.class);
                            String currentStatus = dataSnapshot.child("currentStatus").getValue(String.class);
                            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                            //Sends the notification (+ logging) to the user about modified item.
                            if (currentStatus != null && itemType != null && itemAvailDateString != null && itemName != null){
                                Date itemAvailDate = new Date();
                                try {
                                    logger.info("date is "+itemAvailDateString);
                                    itemAvailDate = format.parse(itemAvailDateString);
                                    if (currentStatus.equalsIgnoreCase("Available") && isItemAvailable(itemAvailDate) && isUserInterested(itemType)) {
                                        sendNotification(itemType, itemName);
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                            //this method was left empty for possible future implementation requirements
                            throw new UnsupportedOperationException("No support for this operation in iteration 2");
                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            //this method was left empty for possible future implementation requirements
                            throw new UnsupportedOperationException("No support for this operation in iteration 2");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            //this method was left empty for possible future implementation requirements
                            throw new UnsupportedOperationException("No support for this operation in iteration 2");
                        }

                    });
                }
                }

            /**
             * This method is called when a child node is changed at the specified database reference.
             * @param dataSnapshot An immutable snapshot of the data at the specified database reference after the child has been updated.
             * @param s The key of the previous child node, or null if it is the first child node.
             */
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //this method was left empty for possible future implementation requirements
                //throw new UnsupportedOperationException("No support for this operation in iteration 2");
            }

            /**
             * This method is called when a child node is removed from the reference.
             * @param dataSnapshot An immutable snapshot of the data at the specified database reference after the child has been removed.
             */
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                //this method was left empty for possible future implementation requirements
                throw new UnsupportedOperationException("No support for this operation in iteration 2");
            }

            /**
             * This method is called when a child node location changes at the specified database reference.
             * @param dataSnapshot An immutable snapshot of the data at the new specified database reference location.
             * @param s The key of the previous child node, or null if it is the first child node.
             */
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //this method was left empty for possible future implementation requirements
                throw new UnsupportedOperationException("No support for this operation in iteration 2");
            }

            /**
             * This method will be triggered in the event that this listener either failed at the server, or is removed as a result of the security and Firebase rules.
             * @param databaseError A description of the error that occurred
             */
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //this method was left empty for possible future implementation requirements
                throw new UnsupportedOperationException("No support for this operation in iteration 2");
            }

        });
    }

    /**
     * This method checks if the item available date is valid (available date is not in the past).
     *
     * @param itemAvailDate The available date of an item.
     * @return True if the item is available and the available date is not in the past, false otherwise.
     */
    //This method checks if the item available date is valid (available date is not in the past)
    boolean isItemAvailable(Date itemAvailDate) {
        // adapted from A3
        Calendar currentCalendar = Calendar.getInstance();
        Date currentTime = currentCalendar.getTime();

        return currentTime.after(itemAvailDate);
    }

    /**
     * This method checks if the itemType matches the receiver's item type preference.
     *
     * @param itemType The type of an item.
     * @return True if the item type matches the receiver's preference, false otherwise.
     */
    //This method checks if the itemType matches the receiver's item type preference.
    boolean isUserInterested(String itemType) {
        if (itemInterested.toLowerCase(Locale.ROOT).equalsIgnoreCase("all")){
            return true;
        }
        else {
            return itemType.equalsIgnoreCase(itemInterested);
        }
    }

    /**
     * This method is responsible for sending the notification.
     *
     * @param itemType The type of an item.
     * @param itemName The name of an item.
     */
    //    https://developer.android.com/develop/ui/views/notifications/build-notification
    // This method is responsible for sending the notification.
    void sendNotification(String itemType, String itemName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
        builder.setMessage("A new item that you are interested is added or modified!\nItem Type: "
                        +itemType+"\nItem Name: "+itemName)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
