package com.example.onlinebartertrader;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoginActivityUnitTest {
    static com.example.onlinebartertrader.LoginActivity LoginActivity;

    @BeforeClass
    public static void setup() {
        LoginActivity = new LoginActivity();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    //*** User story 5, AT3 **/
    @Test
    public void checkIfEmailAddressIsEmpty() {
        assertTrue(LoginActivity.isEmptyEmail(""));
        assertFalse(LoginActivity.isEmptyEmail("xyz$56"));
    }

    //*** User story 5, AT3 **/
    @Test
    public void checkIfPasswordIsEmpty() {
        assertTrue(LoginActivity.isEmptyPassword(""));
        assertFalse(LoginActivity.isEmptyPassword("xyz$56"));
    }

    //*** User story 5, AT1 **/
    @Test
    public void checkIfEmailIsValid() {
        assertTrue(LoginActivity.isValidEmailAddress("barter.system@university.ca"));
        assertTrue(LoginActivity.isValidEmailAddress("aap2212@canada.com"));
    }

    //*** User story 5, AT2 **/
    @Test
    public void checkIfEmailIsInvalid() {
        assertFalse(LoginActivity.isValidEmailAddress("barter.app.com"));
        assertFalse(LoginActivity.isValidEmailAddress("barter.app@c"));
    }
}


