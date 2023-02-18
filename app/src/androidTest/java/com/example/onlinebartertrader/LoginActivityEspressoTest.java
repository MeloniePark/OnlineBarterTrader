package com.example.onlinebartertrader;

import android.content.Context;
import android.support.test.espresso.intent.Intents;
import android.support.test.runner.AndroidJUnit4;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityEspressoTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> myRule = new ActivityScenarioRule<>(LoginActivity.class);
  //  public IntentsTestRule<LoginActivity> myIntentRule = new IntentsTestRule<>(LoginActivity.class);


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
        onView(withId(R.id.emailAddress)).check(matches(withText(R.string.EMPTY_STRING)));
        onView(withId(R.id.password)).check(matches(withText(R.string.EMPTY_STRING)));
    }

    @Test
    public void checkIfPasswordIDIsEmpty() {
        onView(withId(R.id.emailAddress)).perform(typeText("abc.123@dal.ca"));
        onView(withId(R.id.password)).perform(typeText(""));
        onView(withId(R.id.receiverLoginButton)).perform(click());
        onView(withId(R.id.errorMessage)).check(matches(withText(R.string.EMPTY_STRING)));
    }

    @Test
    public void checkIfPasswordIsValid() {
        onView(withId(R.id.emailAddress)).perform(typeText("abc.123@dal.ca"));
        onView(withId(R.id.password)).perform(typeText("D@lhousie.2023"));
        onView(withId(R.id.receiverLoginButton)).perform(click());
        onView(withId(R.id.errorMessage)).check(matches(withText("Password is incorrect.")));
    }

    @Test
    public void checkIfBannerIDIsInvalid() {
        onView(withId(R.id.emailAddress)).perform(typeText("abc.123@dal.ca"));
        onView(withId(R.id.password)).perform(typeText("B88001819"));
        onView(withId(R.id.receiverLoginButton)).perform(click());
        onView(withId(R.id.errorMessage)).check(matches(withText(R.string.EMPTY_STRING)));
    }

    @Test
    public void checkIfEmailIsValid() {
        onView(withId(R.id.emailAddress)).perform(typeText("abc.123@dal.ca"));
        onView(withId(R.id.password)).perform(typeText("D@lhousie.2023"));
        onView(withId(R.id.receiverLoginButton)).perform(click());
        onView(withId(R.id.errorMessage)).check(matches(withText(R.string.EMPTY_STRING)));
    }

    @Test
    public void checkIfEmailIsInvalid() {
        onView(withId(R.id.emailAddress)).perform(typeText("abc123.dal.ca"));
        onView(withId(R.id.password)).perform(typeText("D@lhousie.2023"));
        onView(withId(R.id.receiverLoginButton)).perform(click());
        onView(withId(R.id.errorMessage)).check(matches(withText("Enter Valid Email Address")));
    }
}


