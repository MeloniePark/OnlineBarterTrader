package com.example.onlinebartertrader;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import android.app.NotificationManager;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class AlertUnitTest {

    @BeforeClass
    public static void setup() {
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    //*** User story 6, AT1 **/
    @Test
    public void dateAvailInTheFuture() throws ParseException {
        String dateStr = "3000-04-01";
        Date itemAvailDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        Alert alert = new Alert();
        boolean result = alert.isItemAvailable(itemAvailDate);
        assertFalse(result);
    }

    //*** User story 6, AT1 **/
    @Test
    public void dateAvailInThePast() throws ParseException {
        // Arrange
        String dateStr = "2019-01-01";
        Date itemAvailDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        Alert alert = new Alert();
        boolean result = alert.isItemAvailable(itemAvailDate);
        assertTrue(result);
    }

    //*** User story 6, AT1 **/
    @Test
    public void itemInterested() {
        String itemType = "baby toys";
        Alert alert = new Alert();
        boolean result = alert.isUserInterested(itemType);
        assertTrue(result);
    }

    //*** User story 6, AT1 **/
    @Test
    public void itemNotInterested() {
        String itemType = "transcript";
        Alert alert = new Alert();
        boolean result = alert.isUserInterested(itemType);
        assertFalse(result);
    }
}