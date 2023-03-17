package com.example.onlinebartertrader;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.view.View;

import androidx.test.espresso.FailureHandler;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.hamcrest.Matcher;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ReceiverListEspressoTest<IntentsTestRule> {

    @Rule
    public ActivityScenarioRule<ReceiverLandingPage> myRule = new ActivityScenarioRule<>(ReceiverLandingPage.class);
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

    //*** Iteration 2 User story 3, AT2 **/
    @Test
    public void providerCannotSeeReceiverList() throws InterruptedException {
    }

}