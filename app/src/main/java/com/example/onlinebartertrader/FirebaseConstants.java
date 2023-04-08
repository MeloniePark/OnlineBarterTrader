package com.example.onlinebartertrader;

/**
 * Constants related to Firebase database.
 */
public class FirebaseConstants {

    /**
     * Firebase database URL for our project.
     */
    public static final String FIREBASE_URL = "https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/";
    /**
     * Collection name for users in Firebase database.
     */
    public static final String USERS_COLLECTION = "users";
    /**
     * Collection name for chat messages in Firebase database.
     */
    public static final String CHAT_COLLECTION = "chat";
    /**
     * Private constructor to prevent instantiation.
     */
    private FirebaseConstants() {
        // Required private constructor
    }
}

