package com.agoda.assessment.strategy;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BasicCalculateStrategyTest {

    private BasicCalculateStrategy calculator;
    private int hotelScore;
    private int countryScore;

    @Before
    public void setup() {
        this.calculator = new BasicCalculateStrategy();
        this.hotelScore = 5;
        this.countryScore = 3;
    }

    @Test
    public void doNothingTest() {
        System.out.println("Everything is OK!!!");
    }

    @Test
    public void givenIsNotExistHotelAndCountry_whenApply_thenReturn0PointScoreTest() {
        //given
        boolean isExistHotel = false;
        boolean isExistCountry = false;
        double expectedScore = 0.0;

        //when
        double actual = calculator.apply(isExistHotel, hotelScore, isExistCountry, countryScore);

        //then
        assertThat(actual, is(expectedScore));
    }

    @Test
    public void givenExistHotelAndNotExistCountry_whenApply_thenReturnHotelScoreTest(){
        //given
        boolean isExistHotel = true;
        boolean isExistCountry = false;
        double expectedScore = hotelScore;

        //when
        double actual = calculator.apply(isExistHotel, hotelScore, isExistCountry, countryScore);

        //then
        assertThat(actual, is(expectedScore));
    }

    @Test
    public void givenNotExistHotelAndExistCountry_whenApply_thenReturnCountryScoreTest(){
        //given
        boolean isExistHotel = false;
        boolean isExistCountry = true;
        double expectedScore = countryScore;

        //when
        double actual = calculator.apply(isExistHotel, hotelScore, isExistCountry, countryScore);

        //then
        assertThat(actual, is(expectedScore));
    }

    @Test
    public void givenExistHotelAndExistCountry_whenApply_thenReturnMaxScoreTest(){
        //given
        boolean isExistHotel = true;
        boolean isExistCountry = true;
        double expectedScore = Math.max(hotelScore, countryScore);

        //when
        double actual = calculator.apply(isExistHotel, hotelScore, isExistCountry, countryScore);

        //then
        assertThat(actual, is(expectedScore));
    }
}
