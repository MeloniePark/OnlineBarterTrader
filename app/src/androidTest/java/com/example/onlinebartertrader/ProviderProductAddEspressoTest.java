
package com.example.onlinebartertrader;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
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
public class ProviderProductAddEspressoTest {

    @Rule
    public ActivityScenarioRule<ProviderPostItemActivity> myRule = new ActivityScenarioRule<>(ProviderPostItemActivity.class);
    // We copy and pasted the template from assignment, but Intents rule is not used so far.
    // Commented it out since it sometimes gives an error for now.
    //public IntentsTestRule<ProviderLandingPage> myIntentRule = new IntentsTestRule<>(ProviderLandingPage.class);


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

    //*** Iteration 2 User story 2, AT1 **/
    @Test
    public void checkIfProviderPostItemPageVisible() {
        onView(withId(R.id.productTypeProviderPostItem)).check(matches(withText("Select Product Type")));
        onView(withId(R.id.productNameProviderPostItem)).check(matches(withText(R.string.EMPTY_STRING)));
        onView(withId(R.id.dateOfAvailabilityProviderPostItem)).check(matches(withText(R.string.EMPTY_STRING)));
        onView(withId(R.id.descriptionProviderPostItem)).check(matches(withText(R.string.EMPTY_STRING)));
        onView(withId(R.id.placeOfExchangeProviderPostItem)).check(matches(withText(R.string.EMPTY_STRING)));
        onView(withId(R.id.approximateMarketValueProviderPostItem)).check(matches(withText(R.string.EMPTY_STRING)));
        onView(withId(R.id.preferredExchangesInReturnProviderPostItem)).check(matches(withText(R.string.EMPTY_STRING)));
    }

    //*** Iteration 2 User story 2, AT1 **/
    @Test
    public void checkIfProductPostable() {
        onView(withId(R.id.productTypeMenuProviderPostItem)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("baby toys"))).perform(click());

        onView(withId(R.id.productNameProviderPostItem)).perform(typeText("toy"));
        onView(withId(R.id.productNameProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.dateOfAvailabilityProviderPostItem)).perform(typeText("2023-01-01"));
        onView(withId(R.id.dateOfAvailabilityProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.descriptionProviderPostItem)).perform(typeText("happy toy"));
        onView(withId(R.id.descriptionProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.placeOfExchangeProviderPostItem)).perform(typeText("halifax"));
        onView(withId(R.id.placeOfExchangeProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.approximateMarketValueProviderPostItem)).perform(typeText("123"));
        onView(withId(R.id.approximateMarketValueProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.preferredExchangesInReturnProviderPostItem)).perform(typeText("clothes"));
        onView(withId(R.id.preferredExchangesInReturnProviderPostItem)).perform(closeSoftKeyboard());

        onView(withId(R.id.providerSubmitPostProvider)).perform(click());
        intended(hasComponent(ProviderLandingPage.class.getName()));
    }

    //*** Iteration 2 User story 2, AT1 **/
    @Test
    public void checkIfDateOfAvailEmpty() {
        onView(withId(R.id.productTypeMenuProviderPostItem)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("baby toys"))).perform(click());

        onView(withId(R.id.productNameProviderPostItem)).perform(typeText("toy"));
        onView(withId(R.id.productNameProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.dateOfAvailabilityProviderPostItem)).perform(typeText(""));
        onView(withId(R.id.dateOfAvailabilityProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.descriptionProviderPostItem)).perform(typeText("happy toy"));
        onView(withId(R.id.descriptionProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.placeOfExchangeProviderPostItem)).perform(typeText("halifax"));
        onView(withId(R.id.placeOfExchangeProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.approximateMarketValueProviderPostItem)).perform(typeText("123"));
        onView(withId(R.id.approximateMarketValueProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.preferredExchangesInReturnProviderPostItem)).perform(typeText("clothes"));
        onView(withId(R.id.preferredExchangesInReturnProviderPostItem)).perform(closeSoftKeyboard());

        onView(withId(R.id.providerSubmitPostProvider)).perform(click());
        onView(withId(R.id.errorMessageProviderProductAdd)).check(matches(withText(R.string.EMPTY_DATE_OF_AVAILABILITY)));
    }

    //*** Iteration 2 User story 2, AT1 **/
    @Test
    public void checkIfDateOfAvailInvalid() {
        onView(withId(R.id.productTypeMenuProviderPostItem)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("baby toys"))).perform(click());

        onView(withId(R.id.productNameProviderPostItem)).perform(typeText("toy"));
        onView(withId(R.id.productNameProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.dateOfAvailabilityProviderPostItem)).perform(typeText("Friday"));
        onView(withId(R.id.dateOfAvailabilityProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.descriptionProviderPostItem)).perform(typeText("happy toy"));
        onView(withId(R.id.descriptionProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.placeOfExchangeProviderPostItem)).perform(typeText("halifax"));
        onView(withId(R.id.placeOfExchangeProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.approximateMarketValueProviderPostItem)).perform(typeText("123"));
        onView(withId(R.id.approximateMarketValueProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.preferredExchangesInReturnProviderPostItem)).perform(typeText("clothes"));
        onView(withId(R.id.preferredExchangesInReturnProviderPostItem)).perform(closeSoftKeyboard());

        onView(withId(R.id.providerSubmitPostProvider)).perform(click());
        onView(withId(R.id.errorMessageProviderProductAdd)).check(matches(withText(R.string.INVALID_DATE_OF_AVAILABILITY)));
    }

    //*** Iteration 2 User story 2, AT1 **/
    @Test
    public void checkIfDescriptionEmpty() {
        onView(withId(R.id.productTypeMenuProviderPostItem)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("baby toys"))).perform(click());

        onView(withId(R.id.productNameProviderPostItem)).perform(typeText("toy"));
        onView(withId(R.id.productNameProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.dateOfAvailabilityProviderPostItem)).perform(typeText("2023-03-01"));
        onView(withId(R.id.dateOfAvailabilityProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.descriptionProviderPostItem)).perform(typeText(""));
        onView(withId(R.id.descriptionProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.placeOfExchangeProviderPostItem)).perform(typeText("halifax"));
        onView(withId(R.id.placeOfExchangeProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.approximateMarketValueProviderPostItem)).perform(typeText("123"));
        onView(withId(R.id.approximateMarketValueProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.preferredExchangesInReturnProviderPostItem)).perform(typeText("clothes"));
        onView(withId(R.id.preferredExchangesInReturnProviderPostItem)).perform(closeSoftKeyboard());

        onView(withId(R.id.providerSubmitPostProvider)).perform(click());
        onView(withId(R.id.errorMessageProviderProductAdd)).check(matches(withText(R.string.EMPTY_ITEM_DESCRIPTION)));
    }

    //*** Iteration 2 User story 2, AT1 **/
    @Test
    public void checkIfPlaceOfExchangeEmpty() {
        onView(withId(R.id.productTypeMenuProviderPostItem)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("baby toys"))).perform(click());

        onView(withId(R.id.productNameProviderPostItem)).perform(typeText("toy"));
        onView(withId(R.id.productNameProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.dateOfAvailabilityProviderPostItem)).perform(typeText("2023-03-01"));
        onView(withId(R.id.dateOfAvailabilityProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.descriptionProviderPostItem)).perform(typeText("happy toy"));
        onView(withId(R.id.descriptionProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.placeOfExchangeProviderPostItem)).perform(typeText(""));
        onView(withId(R.id.placeOfExchangeProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.approximateMarketValueProviderPostItem)).perform(typeText("123"));
        onView(withId(R.id.approximateMarketValueProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.preferredExchangesInReturnProviderPostItem)).perform(typeText("clothes"));
        onView(withId(R.id.preferredExchangesInReturnProviderPostItem)).perform(closeSoftKeyboard());

        onView(withId(R.id.providerSubmitPostProvider)).perform(click());
        onView(withId(R.id.errorMessageProviderProductAdd)).check(matches(withText(R.string.EMPTY_PLACE_OF_EXCHANGE)));
    }

    //*** Iteration 2 User story 2, AT1 **/
    @Test
    public void checkIfApproximateMarketValueEmpty() {
        onView(withId(R.id.productTypeMenuProviderPostItem)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("baby toys"))).perform(click());

        onView(withId(R.id.productNameProviderPostItem)).perform(typeText("toy"));
        onView(withId(R.id.productNameProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.dateOfAvailabilityProviderPostItem)).perform(typeText("2023-03-01"));
        onView(withId(R.id.dateOfAvailabilityProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.descriptionProviderPostItem)).perform(typeText("happy toy"));
        onView(withId(R.id.descriptionProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.placeOfExchangeProviderPostItem)).perform(typeText("halifax"));
        onView(withId(R.id.placeOfExchangeProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.approximateMarketValueProviderPostItem)).perform(typeText(""));
        onView(withId(R.id.approximateMarketValueProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.preferredExchangesInReturnProviderPostItem)).perform(typeText("clothes"));
        onView(withId(R.id.preferredExchangesInReturnProviderPostItem)).perform(closeSoftKeyboard());

        onView(withId(R.id.providerSubmitPostProvider)).perform(click());
        onView(withId(R.id.errorMessageProviderProductAdd)).check(matches(withText(R.string.EMPTY_APPROXIMATE_MARKET_VALUE)));
    }

    //*** Iteration 2 User story 2, AT1 **/
    @Test
    public void checkIfPreferredExchangesInReturnEmpty() {
        onView(withId(R.id.productTypeMenuProviderPostItem)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("baby toys"))).perform(click());

        onView(withId(R.id.productNameProviderPostItem)).perform(typeText("toy"));
        onView(withId(R.id.productNameProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.dateOfAvailabilityProviderPostItem)).perform(typeText("2023-03-01"));
        onView(withId(R.id.dateOfAvailabilityProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.descriptionProviderPostItem)).perform(typeText("happy toy"));
        onView(withId(R.id.descriptionProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.placeOfExchangeProviderPostItem)).perform(typeText("halifax"));
        onView(withId(R.id.placeOfExchangeProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.approximateMarketValueProviderPostItem)).perform(typeText("123"));
        onView(withId(R.id.approximateMarketValueProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.preferredExchangesInReturnProviderPostItem)).perform(typeText(""));
        onView(withId(R.id.preferredExchangesInReturnProviderPostItem)).perform(closeSoftKeyboard());

        onView(withId(R.id.providerSubmitPostProvider)).perform(click());
        onView(withId(R.id.errorMessageProviderProductAdd)).check(matches(withText(R.string.EMPTY_PREFERRED_EXCHANGE_TYPE)));
    }
}