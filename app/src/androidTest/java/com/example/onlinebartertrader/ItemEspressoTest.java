package com.example.onlinebartertrader;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class ItemEspressoTest {

    @Rule
    public ActivityScenarioRule<ItemActivity> myRule = new ActivityScenarioRule<>(ItemActivity.class);
    // We copy and pasted the template from assignment, but Intents rule is not used so far.

    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    /** Iteration 3 User story 1, AT?? **/
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.onlinebartertrader", appContext.getPackageName());
    }

    /** Iteration 3 User story 1, AT?? **/
    @Test
    public void checkIfChatPageIsVisible() {
        onView(withId(R.id.chatWithProviderButton)).check(matches(isDisplayed()));
        onView(withId(R.id.buyNowButton)).check(matches(isDisplayed()));
        onView(withId(R.id.productInformation)).check(matches(isDisplayed()));
    }

    @Test
    public void checkIfSwitchedToChatPage() {
        onView(withId(R.id.chatWithProviderButton)).perform(click());
        intended(hasComponent(ChatActivity.class.getName()));
    }

    /** Iteartion 3 User Story 2, AT??
     * Since this espresso test is for US2, should be commented out when run for US1.*/
    @Test
    public void checkIfSwitchedToTransactionPage(){
        onView(withId(R.id.buyNowButton)).perform(click());
        intended(hasComponent(TransactionActivity.class.getName()));
    }

}




