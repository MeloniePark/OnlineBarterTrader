package com.example.onlinebartertrader;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.util.Log;
import android.widget.EditText;

import androidx.test.runner.AndroidJUnit4;

import com.example.onlinebartertrader.MainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(AndroidJUnit4.class)
public class SearchUnitTest {
    static com.example.onlinebartertrader.search SearchFunctionality;

    private search activity;
    private DatabaseReference mockUsersRef;
    private DatabaseReference mockUserRefForEmail;
    private DatabaseReference mockPreferenceRef;


    @BeforeClass
    public  void setup() {
        SearchFunctionality = new search();
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
        search searchFunctionality = new search();
        searchFunctionality.onCreate(null);
        assertNotNull(searchFunctionality.findViewById(R.id.searchView));
    }

    @Test
    public void onItemSelected_setsPreference() {
        search searchFunctionality = new search();
        searchFunctionality.userRefForEmail = mock(DatabaseReference.class);
        searchFunctionality.onItemSelected(null, null, 1, 0);
        assertEquals("Electronics", searchFunctionality.preference);
    }

    @Test
    public void onNothingSelected_doesNothing() {
        search searchFunctionality = new search();
        searchFunctionality.onNothingSelected(null);
        // no exception should be thrown
    }
}


