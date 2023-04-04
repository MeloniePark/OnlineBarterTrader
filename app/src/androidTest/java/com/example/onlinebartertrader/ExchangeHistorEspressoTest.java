package com.example.onlinebartertrader;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.assertTrue;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
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

    @Test
    public void receiverExchangeHistoryVisibility() {
        onView(withId(R.id.exchangeHistoryButton)).perform(click());
        onView(withId(R.id.receiverTraded)).check(matches(isDisplayed()));
    }


    @Rule
    public ActivityScenarioRule<StatsPageActivity> providerStatsPage = new ActivityScenarioRule<>(
            new Intent(ApplicationProvider.getApplicationContext(), StatsPageActivity.class).putExtra("userRole", "provider")
    );

    @Test
    public void providerExchangeHistoryVisibility() {
        onView(withId(R.id.exchangeHistoryButton)).perform(click());
        onView(withId(R.id.providerListProvider)).check(matches(isDisplayed()));
    }

    @Rule
    public ActivityScenarioRule<ExchangeHistoryActivity> activityScenarioRule =
            new ActivityScenarioRule<>(ExchangeHistoryActivity.class);
    @Test
    public void testClickBackButton() {
        //Launch the activity
        ActivityScenario<ExchangeHistoryActivity> activityScenario = activityScenarioRule.getScenario();

        //Click the back button
        onView(withId(android.R.id.home)).perform(click());

        //Verify that the activity is finished and the previous activity is resumed
        activityScenario.onActivity(activity -> assertTrue(activity.isFinishing()));
    }
}
