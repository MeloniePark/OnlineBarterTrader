package com.example.onlinebartertrader;
import static org.junit.Assert.assertEquals;


import org.junit.AfterClass;
import org.junit.Test;

public class ProviderRatingReceiverObjUnitTest {
    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void testDemoObjTest(){
        ProviderRatingReceiverObj providerRatingReceiverObj;
        providerRatingReceiverObj = new ProviderRatingReceiverObj("tn785083@dal.ca","45");
        providerRatingReceiverObj.addNewRating(1);
        providerRatingReceiverObj.addNewRating(3);
        providerRatingReceiverObj.addNewRating(2);
        assertEquals((5+1+3+2)/4, providerRatingReceiverObj.getAvgRating());
    }
}
