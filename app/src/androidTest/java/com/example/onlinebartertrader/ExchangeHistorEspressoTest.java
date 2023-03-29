package com.example.onlinebartertrader;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ExchangeHistorEspressoTest {
    @Rule
    public ActivityScenarioRule<StatsPageActivity> receiverStatsPage = new ActivityScenarioRule<>(
            new Intent(ApplicationProvider.getApplicationContext(), StatsPageActivity.class).putExtra("userRole", "receiver")
    );

    @Rule
    public ActivityScenarioRule<StatsPageActivity> providerStatsPage = new ActivityScenarioRule<>(
            new Intent(ApplicationProvider.getApplicationContext(), StatsPageActivity.class).putExtra("userRole", "provider")
    );

    @Test
    public void receiverExchangeHistoryVisibility() {
        onView(withId(R.id.exchangeHistoryButton)).perform(click());
        onView(withId(R.id.receiverTraded)).check(matches(isDisplayed()));
    }

    @Test
    public void providerExchangeHistoryVisibility() {
        onView(withId(R.id.exchangeHistoryButton)).perform(click());
        onView(withId(R.id.providerListProvider)).check(matches(isDisplayed()));
    }

//    String userRole = "Receiver";
//    String userId = "test@dalca";
//    String productName = "Test Product";
//    String dateOfPurchase = "2023-03-28";
//    String cost = "50";
//    String exchangeItem = "Test Exchange Item";
//    String location = "Test Location";
//    String providerId = "test@provider";
//
//    // Assuming you have a method in ExchangeHistoryActivity that sets the userRole and userId
//        exchangeHistoryActivity.setUserRoleAndId(userRole, userId);
//
//    // Assuming you have a method in ExchangeHistoryActivity to check if the correct data is displayed
//    assertTrue(exchangeHistoryActivity.isExchangeHistoryDisplayed(userRole, userId, productName, dateOfPurchase, cost, exchangeItem, location, providerId));
}
