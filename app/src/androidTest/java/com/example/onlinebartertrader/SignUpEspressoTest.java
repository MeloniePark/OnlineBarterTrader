package com.example.onlinebartertrader;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
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

        onView(withId(R.id.signUpButton)).perform(click());
        //onView(withId(R.id.emailAddress)).check(matches(withText("")));
        //onView(withId(R.id.password)).check(matches(withText("")));
        //onView(withId(R.id.passwordMatch)).check(matches(withText("")));
    }

    @Test
    public void checkIfEmailIsEmpty() {
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.emailAddress)).perform(typeText(""));
        onView(withId(R.id.password)).perform(typeText("tianzheng123"));
        onView(withId(R.id.passwordMatch)).perform(typeText("tianzheng123"));
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.errorMessage)).check(matches(withText("Empty Email")));
    }

    @Test
    public void checkIfEmailIsInvalidate() {
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.emailAddress)).perform(typeText("tn785083dal.ca"));
        onView(withId(R.id.password)).perform(typeText("tianzheng123"));
        onView(withId(R.id.passwordMatch)).perform(typeText("tianzheng123"));
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.errorMessage)).check(matches(withText("Invalidate Email Address")));
    }

    @Test
    public void checkIfPasswordEmpty() {
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.emailAddress)).perform(typeText("tn785083@dal.ca"));
        onView(withId(R.id.password)).perform(typeText(""));
        onView(withId(R.id.passwordMatch)).perform(typeText("tianzheng123"));
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.errorMessage)).check(matches(withText("Empty Password")));
    }

    @Test
    public void checkIfPasswordMatchEmpty() {
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.emailAddress)).perform(typeText("tn785083@dal.ca"));
        onView(withId(R.id.password)).perform(typeText("tianzheng123"));
        onView(withId(R.id.passwordMatch)).perform(typeText(""));
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.errorMessage)).check(matches(withText("Have to conform your Password")));
    }

    @Test
    public void checkIfPasswordValidate() {
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.emailAddress)).perform(typeText("tn785083@dal.ca"));
        onView(withId(R.id.password)).perform(typeText("tianzheng123"));
        onView(withId(R.id.passwordMatch)).perform(typeText("tianzheng123"));
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.errorMessage)).check(matches(withText("")));
    }

    @Test
    public void checkIfPasswordInValidate() {
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.emailAddress)).perform(typeText("tn785083@dal.ca"));
        onView(withId(R.id.password)).perform(typeText("tianzheng123"));
        onView(withId(R.id.passwordMatch)).perform(typeText("tianzheng"));
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.errorMessage)).check(matches(withText("Password did not mach")));
    }

    //Don't know why intent did not work
    //import static androidx.test.espresso.intent.Intents.intended;
    //The .intent. is red in here
    /*@Test
    public void checkIfSwitched2WelcomePage() {
        onView(withId(R.id.returnButton)).perform(click());
        intended(hasComponent(WelcomeActivity.class.getName()));
    }*/
}