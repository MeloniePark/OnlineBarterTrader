package com.example.onlinebartertrader;
import android.view.KeyEvent;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class SearchEspressoTest {

    @Rule
    public ActivityScenarioRule<MainActivity> myRule = new ActivityScenarioRule<>(MainActivity.class);

    @BeforeClass
    public static void setup(){
        Intents.init(); }

    @AfterClass
    public static void tearDown() {
        // Add any necessary tear down steps here.
    }

    @Test
    public void checkSearchFunction() {
        onView(withId(R.id.searchView)).perform(click());
        onView(withId(R.id.searchView)).perform(click());
    }

    @Test
    public void checkWholeTextSearch() {
        onView(withId(R.id.searchView)).perform(click());
        onView(withId(R.id.searchView)).perform(typeText("baby toys"));
        onView(withId(R.id.searchView)).perform(click());}

    @Test
    public void checkIncompleteTextSearch() {
        onView(withId(R.id.searchView)).perform(click());
        onView(withId(R.id.searchView)).perform(typeText("ba"));
        onView(withId(R.id.searchView)).perform(click());

    }

    @Test
    public void checkUppercaseSearch() {
        onView(withId(R.id.searchView)).perform(click());
        onView(withId(R.id.searchView)).perform(typeText("BABY TOYS"));
        onView(withId(R.id.searchView)).perform(click()); }

    @Test
    public void checkTrimSearch() {
        onView(withId(R.id.searchView)).perform(click());
        onView(withId(R.id.searchView)).perform(typeText("Apple"));
        onView(withId(R.id.searchView)).perform(click()); }

    @Test
    public void checkNoResultSearch() {
        onView(withId(R.id.searchView)).perform(click());
        onView(withId(R.id.searchView)).perform(typeText("Abble"));
        onView(withId(R.id.searchView)).perform(ViewActions.pressKey(KeyEvent.KEYCODE_ENTER));
    }
}//package com.example.onlinebartertrader;
//import androidx.test.espresso.Espresso;
//import androidx.test.espresso.action.ViewActions;
//import androidx.test.espresso.assertion.ViewAssertions;
//import androidx.test.espresso.matcher.RootMatchers;
//import androidx.test.espresso.matcher.ViewMatchers;
//import androidx.test.ext.junit.rules.ActivityScenarioRule;
//import androidx.test.filters.LargeTest;
//import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
//import androidx.test.platform.app.InstrumentationRegistry;
//import androidx.test.rule.GrantPermissionRule;
//
//import com.example.onlinebartertrader.R;
//import com.example.onlinebartertrader.SearchFunctionality;
//
//import org.junit.AfterClass;
//import org.junit.BeforeClass;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
//import static androidx.test.espresso.matcher.ViewMatchers.withHint;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;
//import static org.hamcrest.Matchers.allOf;
//import static org.hamcrest.Matchers.containsString;
//import static org.hamcrest.Matchers.not;
//
//@RunWith(AndroidJUnit4ClassRunner.class)
//@LargeTest
//public class SearchFunctionalityEspressoTest {
//
//    @Rule
//    public ActivityScenarioRule<SearchFunctionality> activityRule =
//            new ActivityScenarioRule<>(SearchFunctionality.class);
//
//    @Rule
//    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);
//
//    @BeforeClass
//    public static void setUp() {
//        // Add any necessary setup steps here.
//    }
//
//    @AfterClass
//    public static void tearDown() {
//        // Add any necessary tear down steps here.
//    }
//
//    @Test
//    public void testSearchByProductType() {
//        Espresso.onView(ViewMatchers.withId(R.id.receiver)).perform(ViewActions.click());
//
//        Espresso.onView(ViewMatchers.withId(R.id.product_list))
//                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
//
//        Espresso.onView(ViewMatchers.withId(R.id.search_view))
//                .perform(ViewActions.click());
//
//        Espresso.onView(ViewMatchers.withHint(R.string.search_hint))
//                .perform(ViewActions.typeText("Electronics"));
//
//        Espresso.onView(ViewMatchers.withId(R.id.product_list))
//                .inRoot(RootMatchers.withDecorView(not(activityRule.getActivity().getWindow().getDecorView())))
//                .check(ViewAssertions.matches(ViewMatchers.hasDescendant(withText(containsString("Electronics")))));
//
//        Espresso.onView(ViewMatchers.withId(R.id.search_view))
//                .perform(ViewActions.click());
//
//        Espresso.onView(ViewMatchers.withHint(R.string.search_hint))
//                .perform(ViewActions.typeText("iPhone"));
//
//        Espresso.onView(ViewMatchers.withId(R.id.product_list))
//                .inRoot(RootMatchers.withDecorView(not(activityRule.getActivity().getWindow().getDecorView())))
//                .check(ViewAssertions.matches(ViewMatchers.hasDescendant(withText(containsString("iPhone")))));
//
//        Espresso.onView(ViewMatchers.withId(R.id.search_view))
//                .perform(ViewActions.click());
//
//        Espresso.onView(ViewMatchers.withHint(R.string.search_hint))
//                .perform(ViewActions.typeText("Apple"));
//
//        Espresso.onView(ViewMatchers.withId(R.id.search_view))
//                .perform(ViewActions.closeSoftKeyboard());
//
//        Espresso.onView(ViewMatchers.withId(R.id.save_filter_button))
//                .perform(ViewActions.click());
//
//        Espresso.onView(withText(R.string.filter_saved_message))
//                .inRoot(RootMatchers.withDecorView(not(activityRule.getActivity().getWindow().getDecorView())))
//                .check(ViewAssertions.matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
//    }
//}

