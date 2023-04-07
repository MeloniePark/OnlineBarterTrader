package com.example.onlinebartertrader;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SearchLocation {
    @BeforeClass
    public static void setup() {
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void locationIsSameAsUser() {
        SearchedItemList searchLocation = new SearchedItemList();
        String expectedLocation = "Halifax";
        String actualLocation = "Halifax";
        assertTrue(searchLocation.checkItemIsPostedByTheReceiver(expectedLocation,actualLocation));
    }

    @Test
    public void locationIsDifferentFromUser() {
        SearchedItemList searchLocation = new SearchedItemList();
        String expectedLocation = "Halifax";
        String actualLocation = "Bedford";
        assertFalse(searchLocation.checkItemIsPostedByTheReceiver(expectedLocation,actualLocation));
    }
}
