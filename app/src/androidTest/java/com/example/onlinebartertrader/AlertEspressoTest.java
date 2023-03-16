package com.example.onlinebartertrader;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.times;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    public ActivityScenarioRule<SignUpActivity> myRule = new ActivityScenarioRule<>(SignUpActivity.class);
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

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.onlinebartertrader", appContext.getPackageName());
    }

    //*** User story 6, AT1 **/
    @Test
    public void itemInterestedIsDisplayed(){
        Alert myAlert = new Alert("test@dalca");
        myAlert.startListening();

        Map<String, Object> myItem = new HashMap<>();
        myItem.put("productType", "furniture");
        myItem.put("productName", "Sofa");
        myItem.put("description", "Sofa so good");
        myItem.put("dateOfAvailability", "2019-01-01");
        myItem.put("placeOfExchange", "Halifax");
        myItem.put("approxMarketValue", "100");
        myItem.put("preferredExchange", "baby toys");

        DatabaseReference providerRef = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/")
                .getReference("Users/Provider/test@dal.ca/items/-1");
        providerRef.setValue(myItem);

        Espresso.onView(withText("Sofa")).check(matches(isDisplayed()));
        providerRef.removeValue();
    }

    //*** User story 6, AT1 **/
    @Test
    public void itemNotInterestedIsNotDisplayed(){
        Alert myAlert = new Alert("test@dalca");
        myAlert.startListening();

        Map<String, Object> myItem = new HashMap<>();
        myItem.put("productType", "furniture");
        myItem.put("productName", "Sofa");
        myItem.put("description", "Sofa so good");
        myItem.put("dateOfAvailability", "2019-01-01");
        myItem.put("placeOfExchange", "Halifax");
        myItem.put("approxMarketValue", "100");
        myItem.put("preferredExchange", "furniture");

        DatabaseReference providerRef = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/")
                .getReference("Users/Provider/test@dal.ca/items/-1");
        providerRef.setValue(myItem);

        Espresso.onView(withText("Sofa")).check(matches(not(isDisplayed())));
        providerRef.removeValue();
    }

    //*** User story 6, AT1 **/
    @Test
    public void itemInFutureIsNotDisplayed(){
        Alert myAlert = new Alert("test@dalca");
        myAlert.startListening();

        Map<String, Object> myItem = new HashMap<>();
        myItem.put("productType", "furniture");
        myItem.put("productName", "Sofa");
        myItem.put("description", "Sofa so good");
        myItem.put("dateOfAvailability", "3000-04-01");
        myItem.put("placeOfExchange", "Halifax");
        myItem.put("approxMarketValue", "100");
        myItem.put("preferredExchange", "baby toys");

        DatabaseReference providerRef = FirebaseDatabase.getInstance("https://onlinebartertrader-52c04-default-rtdb.firebaseio.com/")
                .getReference("Users/Provider/test@dalca/items/-1");
        providerRef.setValue(myItem);

        Espresso.onView(withText("Sofa")).check(matches(not(isDisplayed())));
        providerRef.removeValue();
    }

}