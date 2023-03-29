package com.example.onlinebartertrader;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExchangeHistoryUnitTest {

    static ExchangeHistoryActivity exchangeHistoryActivity;

    @BeforeClass
    public static void setup() {
        exchangeHistoryActivity = new ExchangeHistoryActivity();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
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

        // Assuming you have a method in ExchangeHistoryActivity that sets the userRole and userId
        exchangeHistoryActivity.setUserRoleAndId(userRole, userId);

        // Assuming you have a method in ExchangeHistoryActivity to check if the correct data is displayed
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

        // Assuming you have a method in ExchangeHistoryActivity that sets the userRole and userId
        exchangeHistoryActivity.setUserRoleAndId(userRole, userId);

        // Assuming you have a method in ExchangeHistoryActivity to check if the correct data is displayed
        assertTrue(exchangeHistoryActivity.isExchangeHistoryDisplayed(userRole, userId, productName, dateOfPurchase, cost, exchangeItem, location, receiverId));
    }
}
