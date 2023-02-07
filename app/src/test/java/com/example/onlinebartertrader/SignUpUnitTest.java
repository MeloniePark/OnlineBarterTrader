package com.example.onlinebartertrader;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SignUpUnitTest {
    static SignUpActivity signUpActivity;

    @BeforeClass
    public static void setup() {
        signUpActivity = new SignUpActivity();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void checkIfEmailEmpty(){
        assertTrue(signUpActivity.isEmptyEmail(""));
        assertFalse(signUpActivity.isEmptyEmail("tn785083@dal.ca"));
    }

    @Test
    public void ifEmailIsValid(){
        assertTrue(signUpActivity.isValidEmail("tn785083@dal.ca"));
        assertTrue(signUpActivity.isValidEmail("hou1871049656@gmail.com"));
        //If it does not have @
        assertFalse(signUpActivity.isValidEmail("hou1871049656gmail.com"));
        //If it does not have .com or .ca, if you don't want to do this, just delete the code below
        assertFalse(signUpActivity.isValidEmail("hou1871049656@gmail"));
    }

    @Test
    public void checkIfPasswordEmpty(){
        assertTrue(signUpActivity.isEmptyPassword(""));
        assertFalse(signUpActivity.isEmptyPassword("tn785083@dal.ca"));
    }

    @Test
    public void checkIfPasswordIsSame(){
        assertTrue(signUpActivity.isSamePassword("tianzheng123","tianzheng123"));
        assertFalse(signUpActivity.isSamePassword("tianzheng123","tianzheng12"));
    }
}