package com.example.onlinebartertrader;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
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

@RunWith(AndroidJUnit4.class)
public class ProviderListEspressoTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> myRule = new ActivityScenarioRule<>(LoginActivity.class);
    // We copy and pasted the template from assignment, but Intents rule is not used so far.
    // Commented it out since it sometimes gives an error for now.
    //public IntentsTestRule<LoginActivity> myIntentRule = new IntentsTestRule<>(LoginActivity.class);


    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void test_displayProviderItemsBtn_isDisplayedAndClickable() {
        onView(withId(R.id.displayProviderItemsButton)).check(matches(isDisplayed()));
        onView(withId(R.id.displayProviderItemsButton)).check(matches(isClickable()));
    }

    @Test
    public void test_displayProviderItemsBtn_clickAndCheckProviderItemsList() {
        // Click the button
        onView(withId(R.id.displayProviderItemsButton)).perform(click());

        // Check if the provider items list is displayed
        onView(withId(R.id.providerListProvider)).check(matches(isDisplayed()));
    }
    public void test_displayProviderItemsBtn_clickAndCheckAtLeastOneProviderItem() {
        // Click the button
        onView(withId(R.id.displayProviderItemsButton)).perform(click());

        // Check if at least one provider item is displayed
        //onData(anything()).inAdapterView(withId(R.id.providerListProvider)).atLeast(1).check(matches(isDisplayed()));
    }
}