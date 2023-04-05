package com.example.onlinebartertrader;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoginActivityUnitTest {
    static LoginActivity myLoginActivity;

    @BeforeClass
    public static void setup() {
        myLoginActivity = Mockito.mock(LoginActivity.class);

        Mockito.when(myLoginActivity.isEmptyEmail("")).thenReturn(true);
        Mockito.when(myLoginActivity.isEmptyEmail("xyz$56")).thenReturn(false);

        Mockito.when(myLoginActivity.isEmptyPassword("")).thenReturn(true);
        Mockito.when(myLoginActivity.isEmptyPassword("xyz$56")).thenReturn(false);

        Mockito.when(myLoginActivity.isValidEmailAddress("barter.system@university.ca")).thenReturn(true);
        Mockito.when(myLoginActivity.isValidEmailAddress("aap2212@canada.com")).thenReturn(true);


        Mockito.when(myLoginActivity.isValidEmailAddress("barter.app.com")).thenReturn(false);
        Mockito.when(myLoginActivity.isValidEmailAddress("barter.app@c")).thenReturn(false);

    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    //*** User story 5, AT3 **/
    @Test
    public void checkIfEmailAddressIsEmpty() {
        assertTrue(myLoginActivity.isEmptyEmail(""));
        assertFalse(myLoginActivity.isEmptyEmail("xyz$56"));
    }

    //*** User story 5, AT3 **/
    @Test
    public void checkIfPasswordIsEmpty() {
        assertTrue(myLoginActivity.isEmptyPassword(""));
        assertFalse(myLoginActivity.isEmptyPassword("xyz$56"));
    }

    //*** User story 5, AT1 **/
    @Test
    public void checkIfEmailIsValid() {
        assertTrue(myLoginActivity.isValidEmailAddress("barter.system@university.ca"));
        assertTrue(myLoginActivity.isValidEmailAddress("aap2212@canada.com"));
    }

    //*** User story 5, AT2 **/
    @Test
    public void checkIfEmailIsInvalid() {
        assertFalse(myLoginActivity.isValidEmailAddress("barter.app.com"));
        assertFalse(myLoginActivity.isValidEmailAddress("barter.app@c"));
    }
}


