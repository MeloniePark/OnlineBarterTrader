package com.example.onlinebartertrader;

public class UserStats {

    boolean checkGivenRating(String ratingFromDB, String rating) {
        return ratingFromDB.equalsIgnoreCase(rating);
    }

    boolean checkTotalAmount(String valuationFromDB, String TotalValue) {
        return valuationFromDB.equalsIgnoreCase(TotalValue);
    }
}