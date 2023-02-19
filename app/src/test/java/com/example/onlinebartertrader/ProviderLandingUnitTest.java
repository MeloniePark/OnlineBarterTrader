package com.example.onlinebartertrader;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public class ProviderLandingUnitTest {
    static ProviderLandingPage providerLandingPage;

    @BeforeClass
    public static void setup() {
        providerLandingPage = new ProviderLandingPage();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    //As discussed with TA, due to the fact that there are no logical functions in provider's
    // landing page yet, there are no unit tests for the provider's landing page for this iteration.
    // As more functional User stories gets assigned in future iteration,
    // the JUnit tests will be added.
}
