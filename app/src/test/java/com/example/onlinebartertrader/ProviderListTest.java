package com.example.onlinebartertrader;

import static org.junit.Assert.assertEquals;



import android.app.Instrumentation;
import android.widget.Button;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProviderListTest {
    private ActivityScenario<ProviderLandingPage> scenario;
    static ProviderListTest ProviderListTest;

    @BeforeClass
    public static void setup() {
        ProviderListTest = new ProviderListTest();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    //As discussed with TA, due to the fact that there are no logical functions in provider's
    // landing page yet, there are no unit tests for the provider's landing page for this iteration.
    // As more functional User stories gets assigned in future iteration,
    // the JUnit tests will be added.

    @Test
    public void test_displayProviderItemsBtn_textIsCorrect() {
        Button button = ProviderLandingPage.findViewById(R.id.displayProviderItemsButton);
        String buttonText = button.getText().toString();
        assertEquals("Display Provider Items", buttonText);
    }
    @Test
    public void test_displayProviderItemsBtn_clickLaunchesProviderLandingPageActivity() {
        // Register the activity that needs to be launched on button click
        Intents.init();
        Intent intent = new Intent(activity, ProviderLandingPage.class);
        intending(hasComponent(ProviderListTest.class.getName())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, intent));

        // Perform button click
        onView(withId(R.id.displayProviderItemsButton)).perform(click());

        // Check if the activity is launched
        intended(hasComponent(ProviderListTest.class.getName()));
        Intents.release();
    }


    @Test
    public void test_displayProviderItemsBtn_isEnabled() {
        Button button = activity.findViewById(R.id.displayProviderItemsButton);
        assertTrue(button.isEnabled());
    }
}
