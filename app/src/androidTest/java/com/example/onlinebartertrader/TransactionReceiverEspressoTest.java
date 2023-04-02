package com.example.onlinebartertrader;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
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

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TransactionReceiverEspressoTest {

    @Rule
    public ActivityScenarioRule<TransactionActivity> myRule = new ActivityScenarioRule<>(TransactionActivity.class);
    // We copy and pasted the template from assignment, but Intents rule is not used so far.

    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }


    //*** User story 3, AT1 **/
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.onlinebartertrader", appContext.getPackageName());
    }

    //*** User story 3, AT1 **/
    @Test
    public void checkIfTransactionPageIsVisible() {
        onView(withId(R.id.receiverProductInExchange)).check(matches(isDisplayed()));
        onView(withId(R.id.receiverTransactionEstCost)).check(matches(isDisplayed()));
        onView(withId(R.id.receiverTransactionConfirmBtn)).check(matches(isDisplayed()));
    }

    //*** User story 3, AT1 **/
    @Test
    public void checkIfTransactionCanBeConfirmed() {
        onView(withId(R.id.receiverProductInExchange)).perform(typeText("exchange product"));
        onView(withId(R.id.receiverProductInExchange)).perform(closeSoftKeyboard());
        onView(withId(R.id.receiverTransactionEstCost)).perform(typeText("77"));
        onView(withId(R.id.receiverTransactionEstCost)).perform(closeSoftKeyboard());

        onView(withId(R.id.receiverTransactionConfirmBtn)).check(matches(isClickable()));
    }

    //*** User story 3, AT1 **/
    @Test
    public void checkIfReceiverExchangeProductEmpty() {
        onView(withId(R.id.receiverProductInExchange)).perform(typeText(""));
        onView(withId(R.id.receiverProductInExchange)).perform(closeSoftKeyboard());
        onView(withId(R.id.receiverTransactionEstCost)).perform(typeText("77"));
        onView(withId(R.id.receiverTransactionEstCost)).perform(closeSoftKeyboard());

        onView(withId(R.id.receiverTransactionConfirmBtn)).perform(click());
        onView(withId(R.id.transactionReceiverErrorMessage)).check(matches(withText(R.string.EMPTY_RECEIVER_EXCHANGE_PRODUCT)));
    }

    //*** User story 3, AT1 **/
    @Test
    public void checkIfProductEstimatedCostEmpty() {
        onView(withId(R.id.receiverProductInExchange)).perform(typeText("Melonie's tear"));
        onView(withId(R.id.receiverProductInExchange)).perform(closeSoftKeyboard());
        onView(withId(R.id.receiverTransactionEstCost)).perform(typeText(""));
        onView(withId(R.id.receiverTransactionEstCost)).perform(closeSoftKeyboard());

        onView(withId(R.id.receiverTransactionConfirmBtn)).perform(click());
        onView(withId(R.id.transactionReceiverErrorMessage)).check(matches(withText(R.string.EMPTY_RECEIVER_EST_ITEM_COST)));
    }

    //*** User story 3, AT1 **/
    @Test
    public void checkIfProductEstimatedCostInvalid() {
        onView(withId(R.id.receiverProductInExchange)).perform(typeText("Melonie's tear"));
        onView(withId(R.id.receiverProductInExchange)).perform(closeSoftKeyboard());
        onView(withId(R.id.receiverTransactionEstCost)).perform(typeText("ten"));
        onView(withId(R.id.receiverTransactionEstCost)).perform(closeSoftKeyboard());

        onView(withId(R.id.receiverTransactionConfirmBtn)).perform(click());
        onView(withId(R.id.transactionReceiverErrorMessage)).check(matches(withText(R.string.EMPTY_RECEIVER_EST_ITEM_COST)));
    }


}