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
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
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

    @Test
    public void checkIfLandingPageIsVisible() {
//        onView(withId(R.id.signInButton)).perform(click());
        onView(withId(R.id.emailAddressLogIn)).check(matches(withText(R.string.EMPTY_STRING)));
        onView(withId(R.id.passwordLogIn)).check(matches(withText(R.string.EMPTY_STRING)));
    }

    @Test
    public void checkIfPasswordIDIsEmpty() {
//        onView(withId(R.id.signInButton)).perform(click());
        onView(withId(R.id.emailAddressLogIn)).perform(typeText("a@dal.ca"));
        onView(withId(R.id.passwordLogIn)).perform(typeText(""));
        onView(withId(R.id.receiverLoginButtonLogIn)).perform(click());
        onView(withId(R.id.errorMessage)).check(matches(withText("Either Email Address or Password is empty.")));
    }

    @Test
    public void checkIfPasswordIsValid() {
        onView(withId(R.id.emailAddressLogIn)).perform(typeText("abc.123@dal.ca"));
        onView(withId(R.id.passwordLogIn)).perform(typeText("D@lhousie.2023"));
        onView(withId(R.id.receiverLoginButtonLogIn)).perform(click());
        onView(withId(R.id.errorMessage)).check(matches(withText("Password is incorrect.")));
    }

    @Test
    public void checkIfPasswordIsInvalid() {
        onView(withId(R.id.emailAddressLogIn)).perform(typeText("abc.123@dal.ca"));
        onView(withId(R.id.passwordLogIn)).perform(typeText("B88001819"));
        onView(withId(R.id.receiverLoginButtonLogIn)).perform(click());
        onView(withId(R.id.errorMessage)).check(matches(withText(R.string.EMPTY_STRING)));
    }

    @Test
    public void checkIfEmailIsValid() {
        onView(withId(R.id.emailAddressLogIn)).perform(typeText("abc.123@dal.ca"));
        onView(withId(R.id.passwordLogIn)).perform(typeText("D@lhousie.2023"));
        onView(withId(R.id.receiverLoginButtonLogIn)).perform(click());
        onView(withId(R.id.errorMessage)).check(matches(withText(R.string.EMPTY_STRING)));
    }

    @Test
    public void checkIfEmailIsInvalid() {
        onView(withId(R.id.emailAddressLogIn)).perform(typeText("abc123.dal.ca"));
        onView(withId(R.id.passwordLogIn)).perform(typeText("D@lhousie.2023"));
        onView(withId(R.id.receiverLoginButtonLogIn)).perform(click());
        onView(withId(R.id.errorMessage)).check(matches(withText("Enter Valid Email Address")));
    }
}


