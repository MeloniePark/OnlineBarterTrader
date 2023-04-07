package com.example.onlinebartertrader;

import android.content.Context;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.assertEquals;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.onlinebartertrader.UserStats;

import org.junit.AfterClass;
            import org.junit.BeforeClass;
            import org.junit.Rule;
            import org.junit.Test;
            import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RatingsEspressoTest {
    @Rule
    public ActivityScenarioRule<UserStats> myRule = new ActivityScenarioRule<>(UserStats.class);

    @BeforeClass
    public static void setup(){
        Intents.init(); }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.onlinebartertrader", appContext.getPackageName());
    }

    @Test
    public void checkIfRatingBoxIsVisible() {
        onView(withId(R.id.userRatingText)).check(matches(isDisplayed()));
    }

    @Test
    public void checkIfExchangeHistoryButtonIsVisible() {
        onView(withId(R.id.exchangeHistoryBtn)).check(matches(isDisplayed()));
    }

}
