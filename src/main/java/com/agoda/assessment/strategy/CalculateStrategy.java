package com.agoda.assessment.strategy;

public interface CalculateStrategy {
    double apply(boolean isExistHotel, int hotelScore, boolean isExistCountry, int countryScore);
}
