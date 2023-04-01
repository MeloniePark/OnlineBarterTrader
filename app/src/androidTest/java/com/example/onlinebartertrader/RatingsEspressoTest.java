
package com.example.onlinebartertrader;
        import android.content.Context;

        import static androidx.test.espresso.action.ViewActions.click;
        import static androidx.test.espresso.action.ViewActions.pressKey;
        import static androidx.test.espresso.intent.Intents.intended;
        import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
        import static androidx.test.espresso.matcher.ViewMatchers.withId;
        import static androidx.test.espresso.matcher.ViewMatchers.withText;
        import static org.hamcrest.core.AllOf.allOf;
        import static org.hamcrest.core.Is.is;
        import static org.junit.Assert.assertEquals;

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
public class RatingsEspressoTest {
    @Rule
    public ActivityScenarioRule<UserStats> myRule = new ActivityScenarioRule<>(UserStats.class);

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

}
