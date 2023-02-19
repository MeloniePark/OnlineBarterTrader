package com.example.onlinebartertrader;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public class ReceiverLandingUnitTest {
    static ReceiverLandingPage receiverLandingPage;

    @BeforeClass
    public static void setup() {
        receiverLandingPage = new ReceiverLandingPage();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }
    //As discussed with TA, due to the fact that there are no logical functions in receiver's
    // landing page yet, there are no unit tests for the receiver's landing page for this iteration.
    // As more functional User stories gets assigned in future iteration,
    // the JUnit tests will be added.
}
