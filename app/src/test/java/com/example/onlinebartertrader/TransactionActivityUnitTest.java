package com.example.onlinebartertrader;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class TransactionActivityUnitTest {
    static com.example.onlinebartertrader.TransactionActivity TransactionActivity;

    @BeforeClass
    public static void setup() {
        TransactionActivity = Mockito.mock(TransactionActivity.class);

        Mockito.when(TransactionActivity.isEmptyExchangeItem("baby toys")).thenReturn(false);
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    //*** Iteration 3 User story 2, AT1 **/
    @Test
    public void checkIfExchangeItemIsEmpty() {
        assertTrue(TransactionActivity.isEmptyExchangeItem(""));
        assertFalse(TransactionActivity.isEmptyExchangeItem("Happiness"));
    }

    //*** Iteration 3 User story 2, AT1 **/
    @Test
    public void checkIfValueEmpty() {
        assertTrue(TransactionActivity.isEmptyValue(""));
        assertFalse(TransactionActivity.isEmptyValue("10"));
    }

    //*** Iteration 3 User story 2, AT1 **/
    @Test
    public void checkIfValueValid() {
        assertTrue(TransactionActivity.isValidValue("10"));
        assertFalse(TransactionActivity.isValidValue("ten"));
    }
}


