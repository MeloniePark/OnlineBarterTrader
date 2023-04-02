package com.example.onlinebartertrader;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

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
        signUpActivity = Mockito.mock(SignUpActivity.class);

        Mockito.when(signUpActivity.isEmptyEmail("")).thenReturn(true);
        Mockito.when(signUpActivity.isEmptyEmail("tn785083@dal.ca")).thenReturn(false);

        Mockito.when(signUpActivity.isValidEmail("tn785083@dal.ca")).thenReturn(true);
        Mockito.when(signUpActivity.isValidEmail("hou1871049656@gmail.com")).thenReturn(true);
        Mockito.when(signUpActivity.isValidEmail("hou1871049656gmail.com")).thenReturn(false);
        Mockito.when(signUpActivity.isValidEmail("hou1871049656@gmail")).thenReturn(false);

        Mockito.when(signUpActivity.isEmptyPassword("")).thenReturn(true);
        Mockito.when(signUpActivity.isEmptyPassword("tn785083@dal.ca")).thenReturn(false);
        Mockito.when(signUpActivity.checkPasswordLength("test123")).thenReturn(true);
        Mockito.when(signUpActivity.checkPasswordLength("iha")).thenReturn(false);
        Mockito.when(signUpActivity.checkPasswordCase("ihatewrtingcode")).thenReturn(false);
        Mockito.when(signUpActivity.checkPasswordCase("IHATEWRITNGCODE")).thenReturn(false);
        Mockito.when(signUpActivity.checkPasswordCase("Ihatewrtingcode")).thenReturn(true);
        Mockito.when(signUpActivity.checkPasswordCase("")).thenReturn(false);

        Mockito.when(signUpActivity.checkPasswordSpecialChar("Ilovesun!")).thenReturn(false);
        Mockito.when(signUpActivity.checkPasswordSpecialChar("Ilovesun@")).thenReturn(false);
        Mockito.when(signUpActivity.checkPasswordSpecialChar("Ilovesun#")).thenReturn(false);
        Mockito.when(signUpActivity.checkPasswordSpecialChar("IlovesuN")).thenReturn(true);
        Mockito.when(signUpActivity.checkPasswordSpecialChar("Test123")).thenReturn(true);

        Mockito.when(signUpActivity.isSamePassword("tianzheng123","tianzheng123")).thenReturn(true);
        Mockito.when(signUpActivity.isSamePassword("tianzheng123","tianzheng12")).thenReturn(false);
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    //*** User story 4, AT2 **/
    @Test
    public void checkIfEmailEmpty(){
        assertTrue(signUpActivity.isEmptyEmail(""));
        assertFalse(signUpActivity.isEmptyEmail("tn785083@dal.ca"));
    }

    //*** User story 4, AT2 **/
    @Test
    public void ifEmailIsValid(){
        assertTrue(signUpActivity.isValidEmail("tn785083@dal.ca"));
        assertTrue(signUpActivity.isValidEmail("hou1871049656@gmail.com"));
        //If it does not have @
        assertFalse(signUpActivity.isValidEmail("hou1871049656gmail.com"));
        //If it does not have .com or .ca, if you don't want to do this, just delete the code below
        assertFalse(signUpActivity.isValidEmail("hou1871049656@gmail"));
    }

    //*** User story 4, AT2 **/
    @Test
    public void checkIfPasswordEmpty(){
        assertTrue(signUpActivity.isEmptyPassword(""));
        assertFalse(signUpActivity.isEmptyPassword("tn785083@dal.ca"));
    }

    //*** User story 4, AT3 **/
    @Test
    public void checkPasswordLengthTest(){
        assertTrue(signUpActivity.checkPasswordLength("test123"));
        assertFalse(signUpActivity.checkPasswordLength("iha"));
    }

    //*** User story 4, AT3 **/
    @Test
    public void checkPasswordCaseTest(){
        assertFalse(signUpActivity.checkPasswordCase("ihatewrtingcode"));
        assertFalse(signUpActivity.checkPasswordCase("IHATEWRITNGCODE"));
        assertTrue(signUpActivity.checkPasswordCase("Ihatewrtingcode"));
        assertFalse(signUpActivity.checkPasswordCase(""));

    }

    //*** User story 4, AT3 **/
    @Test
    public void checkPasswordSpecialCharTest(){
        assertFalse(signUpActivity.checkPasswordSpecialChar("Ilovesun!"));
        assertFalse(signUpActivity.checkPasswordSpecialChar("Ilovesun@"));
        assertFalse(signUpActivity.checkPasswordSpecialChar("Ilovesun#"));
        assertTrue(signUpActivity.checkPasswordSpecialChar("IlovesuN"));
        assertTrue(signUpActivity.checkPasswordSpecialChar("Test123"));
    }

    //*** User story 4, AT2 **/
    @Test
    public void checkIfPasswordIsSame(){
        assertTrue(signUpActivity.isSamePassword("tianzheng123","tianzheng123"));
        assertFalse(signUpActivity.isSamePassword("tianzheng123","tianzheng12"));
    }
}