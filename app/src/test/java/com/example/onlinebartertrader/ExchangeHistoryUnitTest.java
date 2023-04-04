package com.example.onlinebartertrader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.google.firebase.database.FirebaseDatabase;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExchangeHistoryUnitTest {
    static ExchangeHistoryActivity exchangeHistoryActivity;
    static FirebaseDatabase database;
    @BeforeClass
    public static void setup() {
        exchangeHistoryActivity = new ExchangeHistoryActivity();
    }

    @AfterClass
    public static void teardown() {
        System.gc();
    }

    @Test
    public void exchangeHistoryListIsNotNull() {
        assertNotNull(exchangeHistoryActivity.exchangeHistoryList);
    }

    @Test
    public void exchangeHistoryRefIsNotNull() {
        assertNotNull(exchangeHistoryActivity.exchangeHistoryRef);
    }

    @Test
    public void exchangeHistoryRefIsProviderForProviderUserType() {
        exchangeHistoryActivity.setUserType("Provider");
        assertEquals("User/Provider/items", exchangeHistoryActivity.exchangeHistoryRef.toString());
    }

    @Test
    public void exchangeHistoryRefIsReceiverForReceiverUserType() {
        exchangeHistoryActivity.setUserType("Receiver");
        assertEquals("User/Receiver/items", exchangeHistoryActivity.exchangeHistoryRef.toString());
    }
}
