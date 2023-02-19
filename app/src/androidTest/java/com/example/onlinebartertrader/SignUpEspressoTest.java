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
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
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
    public ActivityScenarioRule<SignUpActivity> myRule = new ActivityScenarioRule<>(SignUpActivity.class);
    public IntentsTestRule<SignUpActivity> myIntentRule = new IntentsTestRule<>(SignUpActivity.class);


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
        onView(withId(R.id.emailAddressSignUp)).check(matches(withText("")));
        onView(withId(R.id.passwordSignUp)).check(matches(withText("")));
        onView(withId(R.id.passwordMatchSignUp)).check(matches(withText("")));
    }

    @Test
    public void checkIfEmailIsEmpty() {
        onView(withId(R.id.emailAddressSignUp)).perform(typeText(""));
        onView(withId(R.id.emailAddressSignUp)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordSignUp)).perform(typeText("tianzheng123"));
        onView(withId(R.id.passwordSignUp)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordMatchSignUp)).perform(typeText("tianzheng123"));
        onView(withId(R.id.passwordMatchSignUp)).perform(closeSoftKeyboard());
        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.errorMessageSignup)).check(matches(withText(R.string.EMPTY_EMAIL_ADDRESS)));
    }

    @Test
    public void checkIfEmailIsInvalidate() {
        onView(withId(R.id.emailAddressSignUp)).perform(typeText("tn785083dal.ca"));
        onView(withId(R.id.emailAddressSignUp)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordSignUp)).perform(typeText("tianzheng123"));
        onView(withId(R.id.passwordSignUp)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordMatchSignUp)).perform(typeText("tianzheng123"));
        onView(withId(R.id.passwordMatchSignUp)).perform(closeSoftKeyboard());
        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.errorMessageSignup)).check(matches(withText(R.string.INVALID_EMAIL_ADDRESS)));
    }

    @Test
    public void checkIfPasswordEmpty() {
        onView(withId(R.id.emailAddressSignUp)).perform(typeText("tn785083@dal.ca"));
        onView(withId(R.id.emailAddressSignUp)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordSignUp)).perform(typeText(""));
        onView(withId(R.id.passwordSignUp)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordMatchSignUp)).perform(typeText("tianzheng123"));
        onView(withId(R.id.passwordMatchSignUp)).perform(closeSoftKeyboard());
        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.errorMessageSignup)).check(matches(withText(R.string.EMPTY_PASSWORD)));
    }

    @Test
    public void checkIfPasswordMatchEmpty() {
        onView(withId(R.id.emailAddressSignUp)).perform(typeText("tn785083@dal.ca"));
        onView(withId(R.id.emailAddressSignUp)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordSignUp)).perform(typeText("tianzheng123"));
        onView(withId(R.id.passwordSignUp)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordMatchSignUp)).perform(typeText(""));
        onView(withId(R.id.passwordMatchSignUp)).perform(closeSoftKeyboard());
        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.errorMessageSignup)).check(matches(withText(R.string.EMPTY_PASSWORD)));
    }

    //only work in isolation for the same reason of A2.
    @Test
    public void checkIfPasswordValidate() {
        onView(withId(R.id.emailAddressSignUp)).perform(typeText("tn785083@dal.ca"));
        onView(withId(R.id.emailAddressSignUp)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordSignUp)).perform(typeText("Test123"));
        onView(withId(R.id.passwordSignUp)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordMatchSignUp)).perform(typeText("Test123"));
        onView(withId(R.id.passwordMatchSignUp)).perform(closeSoftKeyboard());
        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        intended(hasComponent(LoginActivity.class.getName()));
    }

    @Test
    public void checkIfPasswordInValidate() {
        onView(withId(R.id.emailAddressSignUp)).perform(typeText("tn785083@dal.ca"));
        onView(withId(R.id.emailAddressSignUp)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordSignUp)).perform(typeText("Test123"));
        onView(withId(R.id.passwordSignUp)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordMatchSignUp)).perform(typeText("Tes123"));
        onView(withId(R.id.passwordMatchSignUp)).perform(closeSoftKeyboard());
        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.errorMessageSignup)).check(matches(withText(R.string.SAME_PASSWORD)));
    }

    @Test
    public void checkIfSwitched2MainPage() {
        onView(withId(R.id.returnButtonSignUp)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }

    @Test
    public void checkIfSwitched2LoginActivityPage() {
        onView(withId(R.id.emailAddressSignUp)).perform(typeText("test@dal.ca"));
        onView(withId(R.id.emailAddressSignUp)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordSignUp)).perform(typeText("Test123"));
        onView(withId(R.id.passwordSignUp)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordMatchSignUp)).perform(typeText("Test123"));
        onView(withId(R.id.passwordMatchSignUp)).perform(closeSoftKeyboard());
        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        //        if run in isolation, use this code
        //        intended(hasComponent(LoginActivity.class.getName()));
        //        otherwise if run the whole espresso test, use this code
        intended(hasComponent(LoginActivity.class.getName()),times(2));
    }
}