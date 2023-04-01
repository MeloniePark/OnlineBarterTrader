package com.example.onlinebartertrader;

        import static org.junit.Assert.assertFalse;
        import static org.junit.Assert.assertTrue;
        import org.junit.AfterClass;
        import org.junit.BeforeClass;
        import org.junit.Test;

public class VisualizationValuationUnitTest {
    @BeforeClass
    public static void setup() {
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void totalAmountIsCorrect() {
        UserStats valuation = new UserStats();
        String expectedValuation1  = "$36.5";
        String actualValuation1 = "$36.5";
        assertTrue(valuation.checkTotalAmount(expectedValuation1,actualValuation1));
        String expectedValuation2 = "$1110";
        String actualValuation2 = "$1110";
        assertTrue(valuation.checkTotalAmount(expectedValuation2,actualValuation2));

    }

    @Test
    public void totalAmountIsIncorrect() {
        UserStats valuation = new UserStats();
        String expectedValuation1 = "$1110";
        String actualValuation1 = "$1200";
        assertFalse(valuation.checkTotalAmount(expectedValuation1,actualValuation1));
        String expectedValuation2 = "$300";
        String actualValuation2 = "$203";
        assertFalse(valuation.checkTotalAmount(expectedValuation2,actualValuation2));
    }
}
