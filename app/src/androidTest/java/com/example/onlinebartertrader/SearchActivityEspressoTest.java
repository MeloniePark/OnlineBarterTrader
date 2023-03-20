package com.example.onlinebartertrader;
import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import androidx.appcompat.widget.SearchView;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.widget.EditText;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class SearchActivityEspressoTest {

    @Rule
    public ActivityScenarioRule<SearchActivity> myRule = new ActivityScenarioRule<>(SearchActivity.class);

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

    //*** Iteration 2 User story 5, AT1 **/
    @Test
    public void testSpinnerSelection() {
        onView(withId(R.id.spinnerSearch)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("baby toys"))).perform(click());
        }

    //*** Iteration 2 User story 5, AT1 **/
    @Test
    public void checkSearchFunction() {
        onView(withId(R.id.searchViewSearch)).perform(click());
        onView(withId(R.id.searchViewSearch)).perform(click());
    }


}