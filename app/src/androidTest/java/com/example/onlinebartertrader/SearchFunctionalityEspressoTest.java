package com.example.onlinebartertrader;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.widget.EditText;
import androidx.test.runner.AndroidJUnit4;
import com.example.onlinebartertrader.MainActivity;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SearchFunctionalityEspressoTest {
    static com.example.onlinebartertrader.SearchFunctionality SearchFunctionality;

    @Rule
    public ActivityScenarioRule<SearchFunctionality> myRule = new ActivityScenarioRule<>(SearchFunctionality.class);

    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.onlinebartertrader", appContext.getPackageName());
    }

    @BeforeClass
    public static void setup() {
        SearchFunctionality = new SearchFunctionality();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void testSearchByProductType() {
        onView(withId(R.id.receiver)).perform(click());

        // When viewing the list of available products
        onView(withId(R.id.product_list)).check(matches(isDisplayed()));

        // Then should be able to use the search feature
        onView(withId(R.id.search_view)).perform(click());

        // Search by product type
        onView(isAssignableFrom(EditText.class))
                .perform(typeText("Electronics"), pressImeActionButton());

        // Verify search results
        onView(withId(R.id.product_list))
                .check(matches(hasDescendant(withText("Electronics"))));

        // Search by product in exchange
        onView(withId(R.id.search_view)).perform(click());
        onView(isAssignableFrom(EditText.class))
                .perform(typeText("iPhone"), pressImeActionButton());

        // Verify search results
        onView(withId(R.id.product_list))
                .check(matches(hasDescendant(withText("iPhone"))));

        // Store search filter as preference
        onView(withId(R.id.search_view)).perform(click());
        onView(isAssignableFrom(EditText.class))
                .perform(typeText("Apple"), pressImeActionButton());
        onView(withId(R.id.search_view)).perform(closeSoftKeyboard());
        onView(withId(R.id.save_filter_button)).perform(click());
        onView(withText(R.string.filter_saved_message)).inRoot(withDecorView(not(SearchFunctionality.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));

    }
}



