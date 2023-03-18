package com.example.onlinebartertrader;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import androidx.test.runner.AndroidJUnit4;

import com.google.firebase.database.DatabaseReference;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class SearchUnitTest {
    static SearchPage SearchFunctionality;

    private SearchPage activity;
    private DatabaseReference mockUsersRef;
    private DatabaseReference mockUserRefForEmail;
    private DatabaseReference mockPreferenceRef;


    @BeforeClass
    public  void setup() {
        SearchFunctionality = new SearchPage();
        //   when(Log.e(any(String.class),any(String.class))).thenReturn(any(Integer.class));
        mockUsersRef = mock(DatabaseReference.class);
        mockUserRefForEmail = mock(DatabaseReference.class);
        mockPreferenceRef = mock(DatabaseReference.class);

        when(mockUsersRef.child("Users/Provider/")).thenReturn(mockUserRefForEmail);

//        activity = new search();
//        activity.database = mock(FirebaseDatabase.class);
//        when(activity.database.getReference("Users/Provider/")).thenReturn(mockUserRefForEmail);
//        when(mockUserRefForEmail.child("preference")).thenReturn(mockPreferenceRef);
    }


    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void testSearchByProductType() {
    }
    @Test
    public void onCreate_setsContentView() {
        SearchPage searchFunctionality = new SearchPage();
        searchFunctionality.onCreate(null);
        assertNotNull(searchFunctionality.findViewById(R.id.searchView));
    }

    @Test
    public void onItemSelected_setsPreference() {
        SearchPage searchFunctionality = new SearchPage();
        searchFunctionality.userRefForEmail = mock(DatabaseReference.class);
        searchFunctionality.onItemSelected(null, null, 1, 0);
        assertEquals("Electronics", searchFunctionality.preference);
    }

    @Test
    public void onNothingSelected_doesNothing() {
        SearchPage searchFunctionality = new SearchPage();
        searchFunctionality.onNothingSelected(null);
        // no exception should be thrown
    }
}


