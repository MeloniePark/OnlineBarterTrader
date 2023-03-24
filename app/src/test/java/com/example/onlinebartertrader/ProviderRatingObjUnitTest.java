package com.example.onlinebartertrader;
import static org.junit.Assert.assertEquals;


import com.google.firebase.database.FirebaseDatabase;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProviderRatingObjUnitTest {
    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void testDemoObjTest(){
        ProviderRatingObj providerRatingObj;
        providerRatingObj = new ProviderRatingObj(5);
        providerRatingObj.addNewRating(1);
        providerRatingObj.addNewRating(3);
        providerRatingObj.addNewRating(2);
        assertEquals((5+1+3+2)/4,providerRatingObj.getAvgRating());
    }
}
