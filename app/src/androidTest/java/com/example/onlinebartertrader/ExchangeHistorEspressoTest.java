package com.example.onlinebartertrader;

import android.content.Context;
import android.content.Intent;
import android.widget.ListView;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
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
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

@RunWith(AndroidJUnit4.class)
public class ExchangeHistorEspressoTest {
   static FirebaseDatabase database;


    @Rule
    public IntentsTestRule<ExchangeHistoryActivity> myIntentRule = new IntentsTestRule<>(ExchangeHistoryActivity.class);

    @BeforeClass
    public static void setup() {
        Intents.init();

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/");

        // Get a reference to the Firebase database
        database = FirebaseDatabase.getInstance();

        // Populate the Firebase database with test data
        DatabaseReference providerRef = database.getReference("Users/Provider/provider@examplecom");
        DatabaseReference receiverRef = database.getReference("Users/Receiver/receiver@examplecom");

        // Set up mock database
        Map<String, String> item1 = new HashMap<>();
        item1.put("productName", "Item 1");
        item1.put("transactionDate", "2022-01-01");
        item1.put("cost", "10");
        item1.put("exchangeItem", "Item 2");
        item1.put("location", "Location 1");
        item1.put("receiverId", "receiver@example.com");

        Map<String, String> item2 = new HashMap<>();
        item2.put("productName", "Item 2");
        item2.put("transactionDate", "2022-02-01");
        item2.put("cost", "20");
        item2.put("exchangeItem", "Item 1");
        item2.put("location", "Location 2");
        item2.put("receiverId", "receiver@example.com");

        database.getReference("Users/Provider/provider@examplecom/items/100").setValue(item1);
        database.getReference("Users/Provider/provider@examplecom/items/120").setValue(item2);

        Intents.release();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.onlinebartertrader", appContext.getPackageName());
    }

       @Rule
    public ActivityScenarioRule<ExchangeHistoryActivity> activityRule =
            new ActivityScenarioRule<>(ExchangeHistoryActivity.class);

    @Test
    public void testProviderExchangeHistoryDisplayed() {
        // Launch the ExchangeHistoryActivity with the provider role
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ExchangeHistoryActivity.class);
        intent.putExtra("userType", "Provider");
        intent.putExtra("emailAddress", "provider@examplecom");
        activityRule.getScenario().onActivity(activity -> {
            activity.setExchangeHistoryRef("Provider", "provider@examplecom", database);
        });

        // Verify that the exchange history list view is displayed and contains the expected items
        onView(withId(R.id.exchange_history_list_view)).check(matches(isDisplayed()));
        onView(withText("Product Name: Item 1\nTransaction Date: 2022-01-01\nCost: 10\nExchange Item: Item 2\nLocation: Location 1\nReceiver ID: receiver@example.com")).check(matches(isDisplayed()));
        onView(withText("Product Name: Item 2\nTransaction Date: 2022-02-01\nCost: 20\nExchange Item: Item 1\nLocation: Location 2\nReceiver ID: receiver@example.com")).check(matches(isDisplayed()));
    }

    @Test
    public void testReceiverExchangeHistoryDisplayed() {
        // Launch the ExchangeHistoryActivity with the receiver role
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ExchangeHistoryActivity.class);
        intent.putExtra("userType", "Receiver");
        intent.putExtra("emailAddress", "receiver@example.com");
        activityRule.getScenario().onActivity(activity -> {
            activity.setExchangeHistoryRef("Receiver", "receiver@example.com", database);
        });

        // Verify that the exchange history list view is displayed and contains the expected items
        onView(withId(R.id.exchange_history_list_view)).check(matches(isDisplayed()));
        onView(withText("Product Name: Item 1\nTransaction Date: 2022-01-01\nCost: 10\nExchange Item: Item 2\nLocation: Location 1\nProvider ID: provider@example.com")).check(matches(isDisplayed()));
        onView(withText("Product Name: Item 2\nTransaction Date: 2022-02-01\nCost: 20\nExchange Item: Item 1\nLocation: Location 2\nProvider ID: provider@example.com")).check(matches(isDisplayed()));
    }
}


