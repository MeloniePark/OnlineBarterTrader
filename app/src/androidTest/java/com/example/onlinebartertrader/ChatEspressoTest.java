package com.example.onlinebartertrader;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ChatEspressoTest {

    @Rule
    public ActivityScenarioRule<ReceiverLandingPage> myRule = new ActivityScenarioRule<>(ReceiverLandingPage.class);
    // We copy and pasted the template from assignment, but Intents rule is not used so far.

    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

}

