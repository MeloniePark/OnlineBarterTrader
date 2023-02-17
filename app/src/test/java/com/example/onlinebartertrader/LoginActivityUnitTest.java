package com.example.onlinebartertrader;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
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

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void checkIfEmailAddressIsEmpty() {
        assertTrue(LoginActivity.isEmptyEmail(""));
        assertFalse(LoginActivity.isEmptyEmail("xyz$56"));
    }

    @Test
    public void checkIfPasswordIsEmpty() {
        assertTrue(LoginActivity.isEmptyPassword(""));
        assertFalse(LoginActivity.isEmptyPassword("xyz$56"));
    }

    @Test
    public void checkIfPasswordIsValid() {
        assertTrue(LoginActivity.isValidPassword("D@lhousie.2023"));
    }

    @Test
    public void checkIfPasswordIsInvalid() {
        assertFalse(LoginActivity.isValidPassword("Dalhousie22"));
        assertFalse(LoginActivity.isValidPassword("9024941234"));
        assertFalse(LoginActivity.isValidPassword("dal"));
        assertFalse(LoginActivity.isValidPassword("@2212"));
    }

    @Test
    public void checkIfEmailIsValid() {
        assertTrue(LoginActivity.isValidEmailAddress("barter.system@university.ca"));
        assertTrue(LoginActivity.isValidEmailAddress("aap2212@canada.com"));
    }

    @Test
    public void checkIfEmailIsInvalid() {
        assertFalse(LoginActivity.isValidEmailAddress("barter.app.com"));
        assertFalse(LoginActivity.isValidEmailAddress("barter.app@c"));
    }
}


