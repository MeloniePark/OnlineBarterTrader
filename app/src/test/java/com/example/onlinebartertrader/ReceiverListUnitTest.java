package com.example.onlinebartertrader;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ReceiverListUnitTest {

    @BeforeClass
    public static void setup() {
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    //*** Iteration 2 User story 3, AT3 **/
    @Test
    public void isTheSameUserCase1() throws ParseException {
        String providerEmail = "test@dal.ca";
        String receiverEmail = "Test@dal.ca";
        ReceiverItemList myReceiverList= new ReceiverItemList();
        assertTrue(myReceiverList.checkItemIsPostedByTheReceiver(providerEmail, receiverEmail));
    }

    //*** Iteration 2 User story 3, AT3 **/
    @Test
    public void isTheSameUserCase2() throws ParseException {
        String providerEmail = "test@dal.ca";
        String receiverEmail = "TEST@DAL.CA";
        ReceiverItemList myReceiverList= new ReceiverItemList();
        assertTrue(myReceiverList.checkItemIsPostedByTheReceiver(providerEmail, receiverEmail));
    }

    //*** Iteration 2 User story 3, AT3 **/
    @Test
    public void isNotTheSameUser() throws ParseException {
        String providerEmail = "test@dal.ca";
        String receiverEmail = "hello@dal.ca";
        ReceiverItemList myReceiverList= new ReceiverItemList();
        assertFalse(myReceiverList.checkItemIsPostedByTheReceiver(providerEmail, receiverEmail));
    }
}