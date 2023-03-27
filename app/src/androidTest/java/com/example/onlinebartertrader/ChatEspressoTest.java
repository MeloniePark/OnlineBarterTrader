package com.example.onlinebartertrader;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

import android.content.Context;

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
public class ChatEspressoTest {

    @Rule
    public ActivityScenarioRule<ChatActivity> myRule = new ActivityScenarioRule<>(ChatActivity.class);
    // We copy and pasted the template from assignment, but Intents rule is not used so far.

    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    /** Iteration 3 User story 1, AT?? **/
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.onlinebartertrader", appContext.getPackageName());
    }


    /** Iteration 3 User story 1, AT?? **/
    @Test
    public void checkIfChatPageIsVisible() {
        onView(withId(R.id.chatSendButton)).check(matches(isDisplayed()));
        onView(withId(R.id.chatWriteMessage)).check(matches(withText("")));
        onView(withId(R.id.chatRecyclerMessagesView)).check(matches(withText("")));
    }


    /** Iteration 3 User story 1, AT?? **/
    @Test
    public void checkMessageCanBeSent() {
        onView(withId(R.id.chatWriteMessage)).perform(typeText("Hello this is the receiver."));
        onView(withId(R.id.chatWriteMessage)).perform(closeSoftKeyboard());
        onView(withId(R.id.chatSendButton)).perform(click());
        intended(hasComponent(ChatActivity.class.getName()));
    }


    /** Iteration 3 User story 1, AT?? **/
    @Test
    public void checkChatMessageIsEmpty(){
        onView(withId(R.id.chatWriteMessage)).perform(typeText(""));
        onView(withId(R.id.chatWriteMessage)).perform(closeSoftKeyboard());
        onView(withId(R.id.chatSendButton)).perform(click());
        onView(withId(R.id.chatSendButton)).perform(closeSoftKeyboard());
    }
}

