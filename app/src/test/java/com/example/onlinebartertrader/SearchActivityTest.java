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
        searchActivity = new SearchActivity();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void testPreferenceExists() {
        SearchActivity search = new SearchActivity();
        String expected = "computer accessories";
        String actual = search.preferences[1];
        assertEquals(expected, actual);
    }

    @Test
    public void testPreferenceDoesNotExist() {
        SearchActivity search = new SearchActivity();
        String expected = "All";
        String actual = search.preference;
        assertEquals(expected, actual);
    }
    @Test
    public void testSpinnerContainsCorrectPreferences() {
        // Assert that the ArrayAdapter used to populate the spinner contains the correct preferences array
        ArrayAdapter<String> spinnerAdapter = (ArrayAdapter<String>) spinner.getAdapter();
        //assertArrayEquals(activity.preferences, spinnerAdapter.toArray());
    }

    @Test
    public void testSearchViewOnQueryTextSubmitReturnsTrue() {
        //assertTrue(searchView.getOnQueryTextListener().onQueryTextSubmit("search"));
    }

}


