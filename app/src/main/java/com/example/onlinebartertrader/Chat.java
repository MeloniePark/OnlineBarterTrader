package com.example.onlinebartertrader;

/**
 * Chat.java
 *
 * Description: Chat class is one of the supporting class for the instnat chat functionality
 *          for the receiver and provider.
 *          The Chat class provides user & chat message getter and setter for the ChatActivity.
 *
 * Reference:   Tutorial on Instant Chat Integration
 */
public class Chat {
    //    keys
    private String username;
    private String chatMessage;

    /**
     * Chat():
     * Firebase requires constructor, so created empty constructor
     */
    public Chat() {
    }

    /**
     * Chat(String username, String chatMessage) :
     *      initialising constructor
     * @param username  String chat user's name.
     * @param chatMessage   String chatMessage user send.
     */
    public Chat(String username, String chatMessage) {
        this.username = username;
        this.chatMessage = chatMessage;
    }

    /**
     * getUsername(): Getter for username
     * @return String username
     */
    public String getUsername() {
        return username;
    }

    /**
     * setUsername(String username) : Setter for username
     * @param username  String username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * getChatMessage() : Getter for chat message
     * @return String chatMessage
     */
    public String getChatMessage() {
        return chatMessage;
    }

    /**
     * setChatMessage(String chatMessage) : setter for chatMessage
     * @param chatMessage String chatMessage
     */
    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }
}

