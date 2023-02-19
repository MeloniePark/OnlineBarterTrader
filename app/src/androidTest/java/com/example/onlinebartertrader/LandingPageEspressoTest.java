package com.example.onlinebartertrader;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
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
public class LandingPageEspressoTest {

    @Rule
    public ActivityScenarioRule<MainActivity> myRule = new ActivityScenarioRule<>(MainActivity.class);
    // We copy and pasted the template from assignment, but Intents rule is not used so far.
    // Commented it out since it sometimes gives an error for now.
    //public IntentsTestRule<MainActivity> myIntentRule = new IntentsTestRule<>(MainActivity.class);


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


    //*** User story 3, AT1 **/
    @Test
    public void checkIfLandingPageIsVisible() {
        onView(withId(R.id.welcomeMessageMainPage)).check(matches(withText("Welcome to Barter Trader!")));
    }

    //*** User story 3, AT1 **/
    @Test
    public void checkIfSwitched2SignUpPage() {
        onView(withId(R.id.signUpButtonMainPage)).perform(click());
        intended(hasComponent(SignUpActivity.class.getName()));
    }

    //*** User story 3, AT1 **/
    @Test
    public void checkIfSwitched2LogInPage() {
        onView(withId(R.id.signInButtonMainPage)).perform(click());
        intended(hasComponent(LoginActivity.class.getName()));
    }
}