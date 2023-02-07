package com.example.onlinebartertrader;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.lang.reflect.WildcardType;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.intent.Intents.intended;
//import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SignUpEspressoTest {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.onlinebartertrader", appContext.getPackageName());
    }


    @Test
    public void checkIfSignUpPageIsVisible() {
        onView(withId(R.id.signUpMain)).perform(click());
        onView(withId(R.id.emailAddress)).check(matches(withText("")));
        onView(withId(R.id.password)).check(matches(withText("")));
        onView(withId(R.id.passwordMatch)).check(matches(withText("")));
    }

    @Test
    public void checkIfEmailIsEmpty() {
        onView(withId(R.id.signUpMain)).perform(click());
        onView(withId(R.id.emailAddress)).perform(typeText(""));
        onView(withId(R.id.password)).perform(typeText("tianzheng123"));
        onView(withId(R.id.passwordMatch)).perform(typeText("tianzheng123"));
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.errorMessage)).check(matches(withText("Empty Email")));
    }

    @Test
    public void checkIfPasswordEmpty() {
        onView(withId(R.id.signUpMain)).perform(click());
        onView(withId(R.id.emailAddress)).perform(typeText("tn785083@dal.ca"));
        onView(withId(R.id.password)).perform(typeText(""));
        onView(withId(R.id.passwordMatch)).perform(typeText("tianzheng123"));
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.errorMessage)).check(matches(withText("Empty Password")));
    }
}