package com.example.onlinebartertrader;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatUnitTest {

    static ChatActivity chatActivity;

    @BeforeClass
    public static void setup() {
        chatActivity = new ChatActivity();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    /** Iteration 3 User story 1, AT?? **/
    @Test
    public void checkRightProvider(){
        //Please click magic mouse for this story.
        assertTrue(chatActivity.checkProvider("mel@dalca"));
        assertFalse(chatActivity.checkProvider(""));
        assertFalse(chatActivity.checkProvider("test@dalca"));
    }

    /** Iteration 3 User story 1, AT?? **/
    @Test
    public void checkRightReceiver(){
        //please log in using test@dal.ca for this unit test
        assertTrue(chatActivity.checkReceiver("test@dalca"));
        assertFalse(chatActivity.checkReceiver("mel@dalca"));
        assertFalse(chatActivity.checkReceiver(""));
    }

    /** Iteration 3 User story 1, AT?? **/
    @Test
    public void checkMessageEmpty(){
        assertTrue(chatActivity.isMessageEmpty(""));
        assertFalse(chatActivity.isMessageEmpty("baby toys"));
    }
}
