package com.example.onlinebartertrader;

import android.content.Context;
import androidx.test.espresso.intent.Intents;
//import android.support.test.runner.AndroidJUnit4;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class LoginActivityEspressoTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> myRule = new ActivityScenarioRule<>(LoginActivity.class);
    public IntentsTestRule<LoginActivity> myIntentRule = new IntentsTestRule<>(LoginActivity.class);


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

    //*** User story 5, AT4 **/
    @Test
    public void checkIfLandingPageIsVisible() {
        onView(withId(R.id.emailAddressLogIn)).check(matches(withText(R.string.EMPTY_STRING)));
        onView(withId(R.id.passwordLogIn)).check(matches(withText(R.string.EMPTY_STRING)));
    }
    //*** User story 5, AT3 **/
    @Test
    public void checkIfPasswordIsEmpty() {
        onView(withId(R.id.emailAddressLogIn)).perform(typeText("a@dal.ca"));
        onView(withId(R.id.emailAddressLogIn)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordLogIn)).perform(typeText(""));
        onView(withId(R.id.passwordLogIn)).perform(closeSoftKeyboard());
        onView(withId(R.id.receiverLoginButtonLogIn)).perform(click());
        onView(withId(R.id.receiverLoginButtonLogIn)).perform(closeSoftKeyboard());
        onView(withId(R.id.errorMessageLogIn)).check(matches(withText(R.string.EMPTY_EMAIL_OR_PASSWORD)));
    }

    //*** User story 5, AT3 **/
    @Test
    public void checkIfEmailIsEmpty() {
        onView(withId(R.id.emailAddressLogIn)).perform(typeText(""));
        onView(withId(R.id.emailAddressLogIn)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordLogIn)).perform(typeText("Test123"));
        onView(withId(R.id.passwordLogIn)).perform(closeSoftKeyboard());
        onView(withId(R.id.receiverLoginButtonLogIn)).perform(click());
        onView(withId(R.id.receiverLoginButtonLogIn)).perform(closeSoftKeyboard());
        onView(withId(R.id.errorMessageLogIn)).check(matches(withText(R.string.EMPTY_EMAIL_OR_PASSWORD)));
    }
    //*** User story 5, AT2 **/
    @Test
    public void checkIfPasswordIsInValid() {
        onView(withId(R.id.emailAddressLogIn)).perform(typeText("test@dal.ca"));
        onView(withId(R.id.emailAddressLogIn)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordLogIn)).perform(typeText("Test1223"));
        onView(withId(R.id.passwordLogIn)).perform(closeSoftKeyboard());
        onView(withId(R.id.receiverLoginButtonLogIn)).perform(click());
        onView(withId(R.id.errorMessageLogIn)).check(matches(withText(R.string.INCORRECT_PASSWORD)));
    }

    //*** User story 5, AT2 **/
    @Test
    public void checkIfEmailIsInvalid() {
        onView(withId(R.id.emailAddressLogIn)).perform(typeText("abc123.dal.ca"));
        onView(withId(R.id.emailAddressLogIn)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordLogIn)).perform(typeText("Test123"));
        onView(withId(R.id.passwordLogIn)).perform(closeSoftKeyboard());
        onView(withId(R.id.receiverLoginButtonLogIn)).perform(click());
        onView(withId(R.id.errorMessageLogIn)).check(matches(withText(R.string.NOT_REGISTERED_EMAIL)));
    }

    //**check User story 5, AT1 in isolation*/
    // not working for the same reason of A2 isolation test
    @Test
    public void checkIfEmailAndPasswordIsValid() {
        onView(withId(R.id.emailAddressLogIn)).perform(typeText("test@dal.ca"));
        onView(withId(R.id.emailAddressLogIn)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordLogIn)).perform(typeText("Test123"));
        onView(withId(R.id.passwordLogIn)).perform(closeSoftKeyboard());
        onView(withId(R.id.receiverLoginButtonLogIn)).perform(click());
        intended(hasComponent(ReceiverLandingPage.class.getName()));
    }
}