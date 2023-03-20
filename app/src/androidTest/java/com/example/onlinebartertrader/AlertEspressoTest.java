package com.example.onlinebartertrader;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.times;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.FailureHandler;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.Root;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.hamcrest.Matcher;
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
public class AlertEspressoTest<IntentsTestRule> {

    @Rule
    public ActivityScenarioRule<ReceiverLandingPage> myRule = new ActivityScenarioRule<>(ReceiverLandingPage.class);
    // We copy and pasted the template from assignment, but Intents rule is not used so far.
    // Commented it out since it sometimes gives an error for now.
    //public IntentsTestRule<SignUpActivity> myIntentRule = new IntentsTestRule<>(SignUpActivity.class);


    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    //*** User story 6, AT1 **/
    @Test
    public void itemInterestedIsDisplayed() throws InterruptedException {
        Map<String, Object> myItem = new HashMap<>();
        myItem.put("productType", "nullType");
        myItem.put("productName", "Sofa");
        myItem.put("description", "Sofa so good");
        myItem.put("dateOfAvailability", "2019-01-01");
        myItem.put("placeOfExchange", "Halifax");
        myItem.put("approxMarketValue", "102");
        myItem.put("preferredExchange", "baby toys");

        DatabaseReference providerRef = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/")
                .getReference("Users/Provider/test@dalca/items/-1");
        providerRef.setValue(myItem);

        myRule.getScenario().onActivity(activity -> {
            DatabaseReference receiverRef = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/")
                    .getReference("Users/Receiver/us6espresso@dalca/preference");
            receiverRef.setValue("nullType");

            new Alert("us6espresso@dalca", activity).startListening();
        });
        onView(isRoot()).inRoot(RootMatchers.isDialog()).check(matches(isDisplayed()));
    }
}