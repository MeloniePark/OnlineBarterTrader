package com.example.onlinebartertrader;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class VisualizationRatingUnitTest {
    @BeforeClass
    public static void setup() {
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void ratingIsCorrect() {
        UserStats valuation = new UserStats();
        String expectedRating1 = "4";
        String actualRating1 = "4";
        assertTrue(valuation.checkGivenRating(expectedRating1,actualRating1));
        String expectedRating2 = "5";
        String actualRating2 = "5";
        assertTrue(valuation.checkGivenRating(expectedRating2,actualRating2));

    }

    @Test
    public void ratingIsIncorrect() {
        UserStats valuation = new UserStats();
        String expectedRating1 = "4";
        String actualRating1 = "5";
        assertFalse(valuation.checkGivenRating(expectedRating1,actualRating1));
        String expectedRating2 = "3";
        String actualRating2 = "2";
        assertFalse(valuation.checkGivenRating(expectedRating2,actualRating2));
    }
}




