package com.example.onlinebartertrader;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
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
import android.content.Context;
import androidx.test.core.app.ApplicationProvider;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    public ActivityScenarioRule<LoginActivity> myRule = new ActivityScenarioRule<>(LoginActivity.class);
    // We copy and pasted the template from assignment, but Intents rule is not used so far.
    // Commented it out since it sometimes gives an error for now.
    //public IntentsTestRule<ProviderLandingPage> myIntentRule = new IntentsTestRule<>(ProviderLandingPage.class);


    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void useAppContext() {
        onView(withId(R.id.emailAddressLogIn)).perform(typeText("test@dal.ca"));
        onView(withId(R.id.emailAddressLogIn)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordLogIn)).perform(typeText("Test123"));
        onView(withId(R.id.passwordLogIn)).perform(closeSoftKeyboard());
        onView(withId(R.id.providerLoginButtonLogIn)).perform(click());
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.onlinebartertrader", appContext.getPackageName());
    }

    //*** User story 3, AT2 **/
    @Test
    public void checkIfPostButtonExist() {
        onView(withId(R.id.emailAddressLogIn)).perform(typeText("test@dal.ca"));
        onView(withId(R.id.emailAddressLogIn)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordLogIn)).perform(typeText("Test123"));
        onView(withId(R.id.passwordLogIn)).perform(closeSoftKeyboard());
        onView(withId(R.id.providerLoginButtonLogIn)).perform(click());
        onView(withId(R.id.providerPostProvider)).perform(click());
    }

    //*** User story 3, AT2 **/
    @Test
    public void checkIfProviderItemsListed() {
        onView(withId(R.id.emailAddressLogIn)).perform(typeText("test@dal.ca"));

        onView(withId(R.id.emailAddressLogIn)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordLogIn)).perform(typeText("Test123"));
        onView(withId(R.id.passwordLogIn)).perform(closeSoftKeyboard());
        onView(withId(R.id.providerLoginButtonLogIn)).perform(click());
        onView(withId(R.id.providerListProvider)).check(matches(isDisplayed()));
    }

    //*** Iteration 2 User story 1**/
    @Test
    public void checkIfLocationVisible() throws InterruptedException {
        onView(withId(R.id.emailAddressLogIn)).perform(typeText("test@dal.ca"));
        onView(withId(R.id.emailAddressLogIn)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordLogIn)).perform(typeText("Test123"));
        onView(withId(R.id.passwordLogIn)).perform(closeSoftKeyboard());
        onView(withId(R.id.providerLoginButtonLogIn)).perform(click());
        Thread.sleep(5000);
        onView(withId(R.id.locationStringProvider)).check(matches(isDisplayed()));
    }
}