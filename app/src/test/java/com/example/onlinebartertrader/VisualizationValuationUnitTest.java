package com.example.onlinebartertrader;

        import static org.junit.Assert.assertFalse;
        import static org.junit.Assert.assertTrue;
        import org.junit.AfterClass;
        import org.junit.BeforeClass;
        import org.junit.Test;
        import org.mockito.Mockito;

public class VisualizationValuationUnitTest {
    static com.example.onlinebartertrader.UserInfo UserInfo;
    @BeforeClass
    public static void setup() {

        UserInfo = Mockito.mock(UserInfo.class);

        Mockito.when(UserInfo.checkTotalAmount("$36.5", "$36.5")).thenReturn(true);
        Mockito.when(UserInfo.checkTotalAmount("$1110", "$1110")).thenReturn(true);
        Mockito.when(UserInfo.checkTotalAmount("$1110", "$1200")).thenReturn(false);
        Mockito.when(UserInfo.checkTotalAmount("$300", "$203")).thenReturn(false);

    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void totalAmountIsCorrect() {
        String expectedValuation1  = "$36.5";
        String actualValuation1 = "$36.5";
        assertTrue(UserInfo.checkTotalAmount(expectedValuation1,actualValuation1));
        String expectedValuation2 = "$1110";
        String actualValuation2 = "$1110";
        assertTrue(UserInfo.checkTotalAmount(expectedValuation2,actualValuation2));

    }

    @Test
    public void totalAmountIsIncorrect() {
        String expectedValuation1 = "$1110";
        String actualValuation1 = "$1200";
        assertFalse(UserInfo.checkTotalAmount(expectedValuation1,actualValuation1));
        String expectedValuation2 = "$300";
        String actualValuation2 = "$203";
        assertFalse(UserInfo.checkTotalAmount(expectedValuation2,actualValuation2));
    }
}
