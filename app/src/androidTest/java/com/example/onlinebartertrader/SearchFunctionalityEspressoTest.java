package com.example.onlinebartertrader;
import android.content.Context;
import android.view.KeyEvent;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class SearchFunctionalityEspressoTest {

    @Rule
    public ActivityScenarioRule<SearchFunctionality> myRule = new ActivityScenarioRule<>(SearchFunctionality.class);

    @BeforeClass
    public static void setup(){
        Intents.init(); }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }
    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.onlinebartertrader", appContext.getPackageName());
    }

//    @Test
//    public void testSearch() {
//            onView(withId(R.id.searchView)).perform(typeText("baby toys"));
//            onView(withId(R.id.searchView)).perform(click());
//           //  Check that the receiver list view has at least one child
//            onView(withId(R.id.receiverListReceiver)).check(matches(hasMinimumChildCount(1)));
//    }
//
//    @Test
//    public void testSpinnerSelection() {
//            // Click the spinner to open the dropdown
//        onView(withId(R.id.spinner)).perform(click());
//
//            // Click on the second item in the dropdown
//        onView(withText("clothes")).perform(click());
//
//            // Check that the selected preference is saved in the database
//            // and that the receiver list view is updated accordingly
//            // (you can use Firebase Test Lab or a similar tool to test this)
//        }
//
//
//    @Test
//    public void checkSearchFunction() {
//        onView(withId(R.id.searchView)).perform(click());
//        onView(withId(R.id.searchView)).perform(click());
//    }
//
//    @Test
//    public void checkWholeTextSearch() {
//        onView(withId(R.id.searchView)).perform(click());
//        onView(withId(R.id.searchView)).perform(typeText("baby toys"));
//        onView(withId(R.id.searchView)).perform(click());}
//
//
//    @Test
//    public void checkNoResultSearch() {
//        onView(withId(R.id.searchView)).perform(click());
//        onView(withId(R.id.searchView)).perform(typeText("Abble"));
//        onView(withId(R.id.searchView)).perform(ViewActions.pressKey(KeyEvent.KEYCODE_ENTER));
//    }
}