
package com.example.onlinebartertrader;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.espresso.Espresso;
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

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ReceiverProductViewingEspressoTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> myRule = new ActivityScenarioRule<>(LoginActivity.class);
    // We copy and pasted the template from assignment, but Intents rule is not used so far.
    // Commented it out since it sometimes gives an error for now.
    public IntentsTestRule<ProviderLandingPage> myIntentRule = new IntentsTestRule<>(ProviderLandingPage.class);


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

    //*** Iteration 2 User story 3, AT3 **/
    @Test
    public void checkIfPostedItemVisible() {
        // log in as provider
        onView(withId(R.id.signInButtonMainPage)).perform(click());
        onView(withId(R.id.emailAddressLogIn)).perform(typeText("test@dal.ca"));
        onView(withId(R.id.emailAddressLogIn)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordLogIn)).perform(typeText("Test123"));
        onView(withId(R.id.passwordLogIn)).perform(closeSoftKeyboard());
        onView(withId(R.id.providerLoginButtonLogIn)).perform(click());
        onView(withId(R.id.providerPostProvider)).perform(click());

        // post item
        onView(withId(R.id.providerPostProvider)).perform(click());
        onView(withId(R.id.productTypeProviderPostItem)).perform(typeText("toy"));
        onView(withId(R.id.productTypeProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.dateOfAvailabilityProviderPostItem)).perform(typeText("2023-"));
        onView(withId(R.id.dateOfAvailabilityProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.descriptionProviderPostItem)).perform(typeText("happy toy"));
        onView(withId(R.id.descriptionProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.placeOfExchangeProviderPostItem)).perform(typeText("halifax"));
        onView(withId(R.id.placeOfExchangeProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.approximateMarketValueProviderPostItem)).perform(typeText("123"));
        onView(withId(R.id.approximateMarketValueProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.preferredExchangesInReturnProviderPostItem)).perform(typeText("book"));
        onView(withId(R.id.preferredExchangesInReturnProviderPostItem)).perform(closeSoftKeyboard());

        onView(withId(R.id.providerSubmitPostProvider)).perform(click());

        Espresso.pressBack();

        // login as receiver
        onView(withId(R.id.signInButtonMainPage)).perform(click());
        onView(withId(R.id.emailAddressLogIn)).perform(typeText("test@dal.ca"));
        onView(withId(R.id.emailAddressLogIn)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordLogIn)).perform(typeText("Test123"));
        onView(withId(R.id.passwordLogIn)).perform(closeSoftKeyboard());
        onView(withId(R.id.receiverLoginButtonLogIn)).perform(click());

        // check if the posted item can be seen
        onView(withId(R.id.availableProductsReceiver)).perform(click());


        // TODO: check if the unique id of the posted item exist
        }
}