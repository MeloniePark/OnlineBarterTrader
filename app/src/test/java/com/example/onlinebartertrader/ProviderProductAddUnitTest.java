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

public class ProviderProductAddUnitTest {

    static ProviderPostItemActivity providerPostItemActivity;

    @BeforeClass
    public static void setup() {
        providerPostItemActivity = new ProviderPostItemActivity();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void checkIfProductTypeFieldIsEmpty() {
        assertTrue(providerPostItemActivity.isProductTypeEmpty(""));
        assertFalse(providerPostItemActivity.isProductTypeEmpty("baby toys"));
    }

    @Test
    public void checkIfProductTypeIsValid(){
        assertFalse(providerPostItemActivity.isProductTypeValid(""));
        assertTrue(providerPostItemActivity.isProductTypeValid("baby toys"));
        assertTrue(providerPostItemActivity.isProductTypeValid("clothes"));
        assertTrue(providerPostItemActivity.isProductTypeValid("computer accessories"));
        assertTrue(providerPostItemActivity.isProductTypeValid("mobile phones"));
        assertTrue(providerPostItemActivity.isProductTypeValid("furniture"));
        if (!providerPostItemActivity.isProductTypeValid("furniture") |
                !providerPostItemActivity.isProductTypeValid("clothes") |
                !providerPostItemActivity.isProductTypeValid("computer accessories") |
                !providerPostItemActivity.isProductTypeValid("mobile phones") |
                !providerPostItemActivity.isProductTypeValid("furniture")){
            assert(false);
        }
    }

    @Test
    public void checkIfDescriptionFieldIsEmpty(){
        assertTrue(providerPostItemActivity.isDescriptionEmpty(""));
        assertFalse(providerPostItemActivity.isDescriptionEmpty("This baby toy is squicky clean new!"));
    }

    @Test
    public void checkIfDateOfAvailabilityFieldIsEmpty(){
        assertTrue(providerPostItemActivity.isDateEmpty(""));
        assertFalse(providerPostItemActivity.isDateEmpty("2023-03-05"));
    }

    @Test
    public void checkIfDateOfAvailabilityIsValid(){
        //
        assertFalse(providerPostItemActivity.isDateValid("2022-03-02"));
        assertFalse(providerPostItemActivity.isDateValid("2024-03-02"));
        assertTrue(providerPostItemActivity.isDateValid("2023-04-12"));
        assertFalse(providerPostItemActivity.isDateValid("02-03-2023"));
        assertFalse(providerPostItemActivity.isDateValid("02-42-2023"));
        assertFalse(providerPostItemActivity.isDateValid("this Friday"));
    }

    @Test
    public void checkIfPlaceOfExchangeIsEmpty(){
        assertTrue(providerPostItemActivity.isPlaceOfExchangeEmpty(""));
        assertFalse(providerPostItemActivity.isPlaceOfExchangeEmpty("Halifax City Hall"));
    }

    @Test
    public void checkIfApproxMarketValueFieldIsEmpty(){
        assertTrue(providerPostItemActivity.isApproxMarketValueEmpty());
        assertFalse(providerPostItemActivity.isApproxMarketValueEmpty(190.00));
    }

    @Test
    public void checkIfApproxMarketValueIsValid(){
        assertFalse(providerPostItemActivity.isApproxMarketValueValid("twenty dollar"));
        assertFalse(providerPostItemActivity.isApproxMarketValueValid(192,394.99));
        assertFalse(providerPostItemActivity.isApproxMarketValueValid("negotiable"));
        assertFalse(providerPostItemActivity.isApproxMarketValueValid("30 dollar"));
        assertTrue(providerPostItemActivity.isApproxMarketValueValid(20.00));
        assertTrue(providerPostItemActivity.isApproxMarketValueValid(70));
    }

    @Test
    public void checkIfPreferredExchangeInReturnFieldIsEmpty(){
        assertTrue(providerPostItemActivity.isPreferredExchangeInReturnEmpty(""));
        assertFalse(providerPostItemActivity.isPreferredExchangeInReturnEmpty("baby toys"));
    }
}
