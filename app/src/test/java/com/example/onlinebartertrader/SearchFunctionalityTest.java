package com.example.onlinebartertrader;
import android.widget.EditText;

import androidx.test.runner.AndroidJUnit4;

import com.example.onlinebartertrader.MainActivity;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SearchFunctionalityTest {
    static com.example.onlinebartertrader.SearchFunctionality SearchFunctionality;

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
    }
}


