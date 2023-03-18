
package com.example.onlinebartertrader;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import androidx.test.espresso.matcher.ViewMatchers;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ReceiverProductViewingEspressoTest {

    static int currentID;
    static FirebaseDatabase database;
    static DatabaseReference currentIDRef;
    @Rule
    public ActivityScenarioRule<ProviderPostItemActivity> myRule = new ActivityScenarioRule<>(ProviderPostItemActivity.class);
    // We copy and pasted the template from assignment, but Intents rule is not used so far.
    // Commented it out since it sometimes gives an error for now.
    public IntentsTestRule<ProviderPostItemActivity> myIntentRule = new IntentsTestRule<>(ProviderPostItemActivity.class);


    @BeforeClass
    public static void setup() {
        Intents.init();
        // Get a reference to the Firebase database
        database = FirebaseDatabase.getInstance();
        currentIDRef = database.getReference("itemID");

        currentIDRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentID = dataSnapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Failed to read value. " + databaseError.getCode());
            }
        });

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
    public void checkIfPostedItemVisible() throws InterruptedException {
        int itemIDBeforeSubmit = currentID;

        // post item
        onView(withId(R.id.productTypeMenuProviderPostItem)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("baby toys"))).perform(click());

        onView(withId(R.id.productNameProviderPostItem)).perform(typeText("toy"));
        onView(withId(R.id.productNameProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.dateOfAvailabilityProviderPostItem)).perform(typeText("2023-01-01"));
        onView(withId(R.id.dateOfAvailabilityProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.descriptionProviderPostItem)).perform(typeText("happy toy"));
        onView(withId(R.id.descriptionProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.placeOfExchangeProviderPostItem)).perform(typeText("halifax"));
        onView(withId(R.id.placeOfExchangeProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.approximateMarketValueProviderPostItem)).perform(typeText("123"));
        onView(withId(R.id.approximateMarketValueProviderPostItem)).perform(closeSoftKeyboard());
        onView(withId(R.id.preferredExchangesInReturnProviderPostItem)).perform(typeText("clothes"));
        onView(withId(R.id.preferredExchangesInReturnProviderPostItem)).perform(closeSoftKeyboard());

        onView(withId(R.id.providerSubmitPostProvider)).perform(click());

        // Used to make sure the itemIDAfterSubmit is retrieved after database is updated
        final CountDownLatch latch = new CountDownLatch(1);
        currentIDRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentID = dataSnapshot.getValue(Integer.class);
                latch.countDown();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Failed to read value. " + databaseError.getCode());
                latch.countDown();
            }
        });
        latch.await();

        int itemIDAfterSubmit = currentID;
        Assert.assertEquals(itemIDBeforeSubmit+1, itemIDAfterSubmit);
    }
}