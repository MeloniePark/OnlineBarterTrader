package com.example.onlinebartertrader;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ProviderViewReceiverRatingEspressoTest<IntentsTestRule> {

    @Rule
    public ActivityScenarioRule<ReceiverRating> myRuleProvider = new ActivityScenarioRule<>(ReceiverRating.class);
    // We copy and pasted the template from assignment, but Intents rule is not used so far.
    // Commented it out since it sometimes gives an error for now.
    //public IntentsTestRule<SignUpActivity> myIntentRule = new IntentsTestRule<>(SignUpActivity.class);


    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.onlinebartertrader", appContext.getPackageName());
    }

    //*** Iteration 3 User story 3**/
    @Test
    public void receiverCannotSeeReceiverRating() throws InterruptedException {
        onView(withId(R.id.ReceiverEmailV)).check(matches(isDisplayed()));
        onView(withId(R.id.ReceiverRatingAvgV)).check(matches(isDisplayed()));
        onView(withId(R.id.CurrRatingInputV)).check(matches(isDisplayed()));
        onView(withId(R.id.SubmitRatingV)).check(matches(isDisplayed()));
        onView(withId(R.id.CancelRatingV)).check(matches(isDisplayed()));
    }

}