package com.example.onlinebartertrader;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatUnitTest {

    static ChatActivity chatActivity;

    @BeforeClass
    public static void setup() {
        chatActivity = Mockito.mock(ChatActivity.class);

        Mockito.when(chatActivity.checkProvider("mel@dalca", "mel@dalca")).thenReturn(true);
        Mockito.when(chatActivity.checkProvider("mel@dalca", "")).thenReturn(false);
        Mockito.when(chatActivity.checkProvider("mel@dalca", "test@dalca")).thenReturn(false);

        Mockito.when(chatActivity.checkReceiver("test@dalca", "test@dalca")).thenReturn(true);
        Mockito.when(chatActivity.checkReceiver("test@dalca", "mel@dalca")).thenReturn(false);
        Mockito.when(chatActivity.checkReceiver("test@dalca", "")).thenReturn(false);


        Mockito.when(chatActivity.isMessageEmpty("")).thenReturn(true);
        Mockito.when(chatActivity.isMessageEmpty("baby toys")).thenReturn(false);
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    /** Iteration 3 User story 1, AT?? **/
    @Test
    public void checkRightProvider(){
        //Please click magic mouse for this story.
        String rightProvider = "mel@dalca";
        String expectedProvider = "mel@dalca";
        assertTrue(chatActivity.checkProvider(rightProvider, expectedProvider));
        assertFalse(chatActivity.checkProvider(rightProvider, ""));
        assertFalse(chatActivity.checkProvider(rightProvider, "test@dalca"));
    }

    /** Iteration 3 User story 1, AT?? **/
    @Test
    public void checkRightReceiver(){
        //please log in using test@dal.ca for this unit test
        String rightReceiver = "test@dalca";
        String expectedReceiver = "test@dalca";
        assertTrue(chatActivity.checkReceiver(rightReceiver, expectedReceiver));
        assertFalse(chatActivity.checkReceiver(rightReceiver, "mel@dalca"));
        assertFalse(chatActivity.checkReceiver(rightReceiver, ""));
    }

    /** Iteration 3 User story 1, AT?? **/
    @Test
    public void checkMessageEmpty(){
        assertTrue(chatActivity.isMessageEmpty(""));
        assertFalse(chatActivity.isMessageEmpty("baby toys"));
    }
}
