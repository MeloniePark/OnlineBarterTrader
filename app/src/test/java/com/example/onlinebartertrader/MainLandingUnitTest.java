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

    //As discussed with TA, due to the fact that there are no logical functions in mainLandingPage,
    //there are no unit tests for main landing page for this iteration.

}
