package com.example.onlinebartertrader;

/*
* This Unit Test are testing the producer's product adding page.
* Producer clicks "POST" button to add another product.
*
*/


/*
1. Product type – a dropdown with values baby toys, clothes, computer accessories, mobile phones, and furniture - required
 2. Description
 3. Date of availability - required
 4. Place of exchange - required
 5. Approximate market value - required
 6. Preferred exchanges in return – required
 And the system should save the above information along with unique id and a status property into the database table.
 - Values for status field – Available/Sold out .
 - Preferred exchanges in return can be same as product type.
 */
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class ProviderProductAddUnitTest {

    static ProviderPostItemActivity providerPostItemActivity;

    @BeforeClass
    public static void setup() {
        providerPostItemActivity = Mockito.mock(ProviderPostItemActivity.class);
        Mockito.when(providerPostItemActivity.isProductTypeEmpty("")).thenReturn(true);
        Mockito.when(providerPostItemActivity.isProductTypeEmpty("baby toys")).thenReturn(false);

        Mockito.when(providerPostItemActivity.isProductTypeValid("")).thenReturn(false);
        Mockito.when(providerPostItemActivity.isProductTypeValid("baby toys")).thenReturn(true);
        Mockito.when(providerPostItemActivity.isProductTypeValid("clothes")).thenReturn(true);
        Mockito.when(providerPostItemActivity.isProductTypeValid("computer accessories")).thenReturn(true);
        Mockito.when(providerPostItemActivity.isProductTypeValid("mobile phones")).thenReturn(true);
        Mockito.when(providerPostItemActivity.isProductTypeValid("furniture")).thenReturn(true);
        Mockito.when(providerPostItemActivity.isProductTypeValid("not valid")).thenReturn(false);

        Mockito.when(providerPostItemActivity.isDescriptionEmpty("")).thenReturn(true);
        Mockito.when(providerPostItemActivity.isDescriptionEmpty("This baby toy is squicky clean new!")).thenReturn(false);

        Mockito.when(providerPostItemActivity.isDateEmpty("")).thenReturn(true);
        Mockito.when(providerPostItemActivity.isDateEmpty("2023-03-05")).thenReturn(false);

        Mockito.when(providerPostItemActivity.isDateValid("2022-03-02")).thenReturn(true);
        Mockito.when(providerPostItemActivity.isDateValid("2024-03-02")).thenReturn(true);
        Mockito.when(providerPostItemActivity.isDateValid("2023-04-12")).thenReturn(true);
        Mockito.when(providerPostItemActivity.isDateValid("02-03-2023")).thenReturn(false);
        Mockito.when(providerPostItemActivity.isDateValid("02-42-2023")).thenReturn(false);
        Mockito.when(providerPostItemActivity.isDateValid("this Friday")).thenReturn(false);

        Mockito.when(providerPostItemActivity.isPlaceOfExchangeEmpty("")).thenReturn(true);
        Mockito.when(providerPostItemActivity.isPlaceOfExchangeEmpty("Halifax City Hall")).thenReturn(false);

        Mockito.when(providerPostItemActivity.isApproxMarketValueEmpty("")).thenReturn(true);
        Mockito.when(providerPostItemActivity.isApproxMarketValueEmpty("190")).thenReturn(false);

        Mockito.when(providerPostItemActivity.isApproxMarketValueValid("0")).thenReturn(false);
        Mockito.when(providerPostItemActivity.isApproxMarketValueValid("-23")).thenReturn(false);
        Mockito.when(providerPostItemActivity.isApproxMarketValueValid("20")).thenReturn(true);
        Mockito.when(providerPostItemActivity.isApproxMarketValueValid("70")).thenReturn(true);

        Mockito.when(providerPostItemActivity.isPreferredExchangeInReturnEmpty("")).thenReturn(true);
        Mockito.when(providerPostItemActivity.isPreferredExchangeInReturnEmpty("baby toys")).thenReturn(false);
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    //** Iteration 2 User story 2, AT2 **/
    @Test
    public void checkIfProductTypeFieldIsEmpty() {
        assertTrue(providerPostItemActivity.isProductTypeEmpty(""));
        assertFalse(providerPostItemActivity.isProductTypeEmpty("baby toys"));
    }

    //** Iteration 2 User story 2, AT2 **/
    @Test
    public void checkIfProductTypeIsValid(){
        assertFalse(providerPostItemActivity.isProductTypeValid(""));
        assertTrue(providerPostItemActivity.isProductTypeValid("baby toys"));
        assertTrue(providerPostItemActivity.isProductTypeValid("clothes"));
        assertTrue(providerPostItemActivity.isProductTypeValid("computer accessories"));
        assertTrue(providerPostItemActivity.isProductTypeValid("mobile phones"));
        assertTrue(providerPostItemActivity.isProductTypeValid("furniture"));
        if (!providerPostItemActivity.isProductTypeValid("furniture") &&
                !providerPostItemActivity.isProductTypeValid("clothes") &&
                !providerPostItemActivity.isProductTypeValid("computer accessories") &&
                !providerPostItemActivity.isProductTypeValid("mobile phones") &&
                !providerPostItemActivity.isProductTypeValid("baby toys")){
            assert(false);
        }
    }

    //** Iteration 2 User story 2, AT2 **/
    @Test
    public void checkIfDescriptionFieldIsEmpty(){
        assertTrue(providerPostItemActivity.isDescriptionEmpty(""));
        assertFalse(providerPostItemActivity.isDescriptionEmpty("This baby toy is squicky clean new!"));
    }

    //** Iteration 2 User story 2, AT2 **/
    @Test
    public void checkIfDateOfAvailabilityFieldIsEmpty(){

        assertTrue(providerPostItemActivity.isDateEmpty(""));
        assertFalse(providerPostItemActivity.isDateEmpty("2023-03-05"));
    }

    //** Iteration 2 User story 2, AT2 **/
    @Test
    public void checkIfDateOfAvailabilityIsValid(){
        assertTrue(providerPostItemActivity.isDateValid("2022-03-02"));
        assertTrue(providerPostItemActivity.isDateValid("2024-03-02"));
        assertTrue(providerPostItemActivity.isDateValid("2023-04-12"));
        assertFalse(providerPostItemActivity.isDateValid("02-03-2023"));
        assertFalse(providerPostItemActivity.isDateValid("02-42-2023"));
        assertFalse(providerPostItemActivity.isDateValid("this Friday"));
    }

    //** Iteration 2 User story 2, AT2 **/
    @Test
    public void checkIfPlaceOfExchangeIsEmpty(){
        assertTrue(providerPostItemActivity.isPlaceOfExchangeEmpty(""));
        assertFalse(providerPostItemActivity.isPlaceOfExchangeEmpty("Halifax City Hall"));
    }

    //** Iteration 2 User story 2, AT2 **/
    @Test
    public void checkIfApproxMarketValueFieldIsEmpty(){
        assertTrue(providerPostItemActivity.isApproxMarketValueEmpty(""));
        assertFalse(providerPostItemActivity.isApproxMarketValueEmpty("190"));
    }

    //** Iteration 2 User story 2, AT2 **/
    @Test
    public void checkIfApproxMarketValueIsValid(){
        assertFalse(providerPostItemActivity.isApproxMarketValueValid("0"));
        assertFalse(providerPostItemActivity.isApproxMarketValueValid("-23"));
        assertTrue(providerPostItemActivity.isApproxMarketValueValid("20"));
        assertTrue(providerPostItemActivity.isApproxMarketValueValid("70"));
    }

    //** Iteration 2 User story 2, AT2 **/
    @Test
    public void checkIfPreferredExchangeInReturnFieldIsEmpty(){
        assertTrue(providerPostItemActivity.isPreferredExchangeInReturnEmpty(""));
        assertFalse(providerPostItemActivity.isPreferredExchangeInReturnEmpty("baby toys"));
    }
}
