package com.agoda.assessment.strategy;

import org.springframework.stereotype.Component;

@Component
public class BasicCalculateStrategy implements CalculateStrategy {

    @Override
    public double apply(boolean isExistHotel, int hotelScore, boolean isExistCountry, int countryScore) {
        if(isExistHotel)
            return hotelScore;

        if(isExistCountry)
            return countryScore;

        return 0;
    }

}
