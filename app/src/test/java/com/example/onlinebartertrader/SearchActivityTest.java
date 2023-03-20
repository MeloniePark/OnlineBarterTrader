package com.example.onlinebartertrader;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SearchActivityTest {
    static SearchActivity searchActivity;
    private SearchActivity activity;
    private Intent intent;
    private ListView receiverItemList;
    private Spinner spinner;

    @BeforeClass
    public static void setup() {
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void itemIsPostedByTheUser() {
        SearchedItemList search = new SearchedItemList();
        String expected = "test@dal.ca";
        String actual = "test@dal.ca";
        assertTrue(search.checkItemIsPostedByTheReceiver(expected,actual));
    }

    @Test
    public void itemIsNotPostedByTheUser() {
        SearchedItemList search = new SearchedItemList();
        String expected = "test@dal.ca";
        String actual = "hello@dal.ca";
        assertFalse(search.checkItemIsPostedByTheReceiver(expected,actual));
    }

}


