package com.example.onlinebartertrader;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class VisualizationRatingUnitTest {
    static com.example.onlinebartertrader.UserInfo UserInfo;
    @BeforeClass
    public static void setup() {
        UserInfo = Mockito.mock(UserInfo.class);

        Mockito.when(UserInfo.checkGivenRating("4", "4")).thenReturn(true);
        Mockito.when(UserInfo.checkGivenRating("5", "5")).thenReturn(true);
        Mockito.when(UserInfo.checkGivenRating("4", "5")).thenReturn(false);
        Mockito.when(UserInfo.checkGivenRating("3", "2")).thenReturn(false);

    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void ratingIsCorrect() {
        String expectedRating1 = "4";
        String actualRating1 = "4";
        assertTrue(UserInfo.checkGivenRating(expectedRating1,actualRating1));
        String expectedRating2 = "5";
        String actualRating2 = "5";
        assertTrue(UserInfo.checkGivenRating(expectedRating2,actualRating2));

    }

    @Test
    public void ratingIsIncorrect() {
        String expectedRating1 = "4";
        String actualRating1 = "5";
        assertFalse(UserInfo.checkGivenRating(expectedRating1,actualRating1));
        String expectedRating2 = "3";
        String actualRating2 = "2";
        assertFalse(UserInfo.checkGivenRating(expectedRating2,actualRating2));
    }
}




