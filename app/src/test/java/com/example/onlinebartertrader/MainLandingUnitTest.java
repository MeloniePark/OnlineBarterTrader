package com.example.onlinebartertrader;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public class MainLandingUnitTest {
    static MainActivity mainActivity;

    @BeforeClass
    public static void setup() {
        mainActivity = new MainActivity();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }



}
