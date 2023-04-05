package com.example.onlinebartertrader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.google.firebase.database.DatabaseReference;
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
        assertTrue(exchangeHistoryActivity.exchangeHistoryListIsNotNull());
    }

    @Test
    public void exchangeHistoryRefIsNotNull() {
        assertNotNull(exchangeHistoryActivity.exchangeHistoryRefIsNotNull());
    }
    
    @Test
    public void receiverExchangeHistory_isDisplayed() {
        String userRole = "Receiver";
        String userId = "test@dalca";
        String productName = "Test Product";
        String dateOfPurchase = "2023-03-28";
        String cost = "50";
        String exchangeItem = "Test Exchange Item";
        String location = "Test Location";
        String providerId = "test@provider";

        //Sets the userRole and userId
        exchangeHistoryActivity.setUserRoleAndId(userRole, userId);

        //Check if the correct data is displayed
        assertTrue(exchangeHistoryActivity.isExchangeHistoryDisplayed(userRole, userId, productName, dateOfPurchase, cost, exchangeItem, location, providerId));
    }

    @Test
    public void providerExchangeHistory_isDisplayed() {
        String userRole = "Provider";
        String userId = "test@dalca";
        String productName = "Test Product";
        String dateOfPurchase = "2023-03-28";
        String cost = "50";
        String exchangeItem = "Test Exchange Item";
        String location = "Test Location";
        String receiverId = "test@receiver";

        //Sets the userRole and userId
        exchangeHistoryActivity.setUserRoleAndId(userRole, userId);

        //Check if the correct data is displayed
        assertTrue(exchangeHistoryActivity.isExchangeHistoryDisplayed(userRole, userId, productName, dateOfPurchase, cost, exchangeItem, location, receiverId));
    }
}
