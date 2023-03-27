package com.example.onlinebartertrader;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * The ItemActivity does not have parts using logic(Just buttons and listing),
 *  the Unit tests are not required.
 */
public class ItemUnitTest {
    static ItemActivity itemActivity;

    @BeforeClass
    public static void setup() {
        itemActivity = new ItemActivity();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }


}
