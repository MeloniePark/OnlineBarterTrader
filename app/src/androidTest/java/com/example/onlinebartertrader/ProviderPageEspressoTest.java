package com.example.onlinebartertrader;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
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
public class ProviderPageEspressoTest {

    @Rule
    public ActivityScenarioRule<ProviderLandingPage> myRule = new ActivityScenarioRule<>(ProviderLandingPage.class);
    // We copy and pasted the template from assignment, but Intents rule is not used so far.
    // Commented it out since it sometimes gives an error for now.
    //public IntentsTestRule<ProviderLandingPage> myIntentRule = new IntentsTestRule<>(ProviderLandingPage.class);


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

    //*** User story 3, AT2 **/
    @Test
    public void checkIfPostButtonExist() {
        onView(withId(R.id.providerPostProvider)).perform(click());
    }

    //*** User story 3, AT2 **/
    @Test
    public void checkIfProviderItemsListed() {
        onView(withId(R.id.providerListProvider)).check(matches(isDisplayed()));
    }

    //*** Iteration 2 User story 1**/
    @Test
    public void checkIfAtDal() throws InterruptedException {
        Thread.sleep(5000);
        onView(withText("MacDonald Bldg, 6300 Coburg Rd, Halifax, NS B3H 4R2, Canada")).check(matches(isDisplayed()));
    }
}