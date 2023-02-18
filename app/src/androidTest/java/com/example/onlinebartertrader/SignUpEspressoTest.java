package com.example.onlinebartertrader;

import android.content.Context;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
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
import static androidx.test.espresso.intent.Intents.times;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;


import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
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

    @Rule
    public ActivityScenarioRule<MainActivity> myRule = new ActivityScenarioRule<>(MainActivity.class);
    public IntentsTestRule<MainActivity> myIntentRule = new IntentsTestRule<>(MainActivity.class);


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
    public void checkIfSignUpPageIsVisible() {

        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.emailAddressSignUp)).check(matches(withText("")));
        onView(withId(R.id.passwordSignUp)).check(matches(withText("")));
        onView(withId(R.id.passwordMatchSignUp)).check(matches(withText("")));
    }

    @Test
    public void checkIfEmailIsEmpty() {
        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.emailAddressSignUp)).perform(typeText(""));
        onView(withId(R.id.passwordSignUp)).perform(typeText("tianzheng123"));
        onView(withId(R.id.passwordMatchSignUp)).perform(typeText("tianzheng123"));
        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.errorMessage)).check(matches(withText("Empty Email")));
    }

    @Test
    public void checkIfEmailIsInvalidate() {
        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.emailAddressSignUp)).perform(typeText("tn785083dal.ca"));
        onView(withId(R.id.passwordSignUp)).perform(typeText("tianzheng123"));
        onView(withId(R.id.passwordMatchSignUp)).perform(typeText("tianzheng123"));
        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.errorMessage)).check(matches(withText("Invalidate Email Address")));
    }

    @Test
    public void checkIfPasswordEmpty() {
        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.emailAddressSignUp)).perform(typeText("tn785083@dal.ca"));
        onView(withId(R.id.passwordSignUp)).perform(typeText(""));
        onView(withId(R.id.passwordMatchSignUp)).perform(typeText("tianzheng123"));
        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.errorMessage)).check(matches(withText("Empty Password")));
    }

    @Test
    public void checkIfPasswordMatchEmpty() {
        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.emailAddressSignUp)).perform(typeText("tn785083@dal.ca"));
        onView(withId(R.id.passwordSignUp)).perform(typeText("tianzheng123"));
        onView(withId(R.id.passwordMatchSignUp)).perform(typeText(""));
        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.errorMessage)).check(matches(withText("Have to conform your Password")));
    }

    @Test
    public void checkIfPasswordValidate() {
        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.emailAddressSignUp)).perform(typeText("tn785083@dal.ca"));
        onView(withId(R.id.passwordSignUp)).perform(typeText("tianzheng123"));
        onView(withId(R.id.passwordMatchSignUp)).perform(typeText("tianzheng123"));
        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.errorMessage)).check(matches(withText("")));
    }

    @Test
    public void checkIfPasswordInValidate() {
        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.emailAddressSignUp)).perform(typeText("tn785083@dal.ca"));
        onView(withId(R.id.passwordSignUp)).perform(typeText("tianzheng123"));
        onView(withId(R.id.passwordMatchSignUp)).perform(typeText("tianzheng"));
        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.errorMessage)).check(matches(withText("Two Passwords should be the same!")));
    }

    @Test
    public void checkIfSwitched2MainPage() {
        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.returnButtonSignUp)).perform(click());
        intended(hasComponent(SignUpActivity.class.getName()));
        intended(hasComponent(MainActivity.class.getName()),times(2));
    }

    @Test
    public void checkIfSwitched2LoginActivityPage() {
        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.emailAddressSignUp)).perform(typeText("tn785083@dal.ca"));
        onView(withId(R.id.passwordSignUp)).perform(typeText("tianzheng123"));
        onView(withId(R.id.passwordMatchSignUp)).perform(typeText("tianzheng123"));
        intended(hasComponent(MainActivity.class.getName()));
        intended(hasComponent(SignUpActivity.class.getName()));
        intended(hasComponent(LoginActivity.class.getName()));
    }
}