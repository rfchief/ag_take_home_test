package com.agoda.assessment.repository;

import com.agoda.assessment.TestDataFactory;
import com.agoda.assessment.repository.impl.HotelRepositoryImpl;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class HotelRepositoryTest {

    private HotelRepository repository;

    @Before
    public void setup() {
        this.repository = new HotelRepositoryImpl(TestDataFactory.getHotelIdStorage());
    }

    @Test
    public void doNothingTest() {
        System.out.println("Everything is OK!!!");
    }

    @Test
    public void givenEmptyHotelIds_whenSave_thenDoNothingTest(){
        //given
        List<Integer> emtpyHotelIds = Lists.newArrayList();
        int initialSize = repository.countAll();

        //when
        repository.save(emtpyHotelIds);

        //then
        int actual = repository.countAll();
        assertThat(actual, is(initialSize));
    }

    @Test
    public void givenHotelIds_whenSave_thenSaveIdsTest() {
        //given
        List<Integer> given = Lists.newArrayList(1, 3, 5, 7);

        //when
        repository.save(given);

        //then
        assertThat(repository.countAll(), greaterThanOrEqualTo(given.size()));
        for (Integer givenHotelId : given)
            assertThat(repository.isExists(givenHotelId), is(true));
    }

    @Test
    public void givenNewHotelId_whenRemove_thenDoNothingTest() {
        //given
        int newHotelId = 5;
        int initSize = repository.countAll();

        //when
        repository.remove(newHotelId);

        //then
        assertThat(repository.countAll(), equalTo(initSize));
    }

    @Test
    public void givenExistHotelId_whenRemove_thenRemovedElementIsNotExistTest() {
        //given
        int givenHotelId = 1;

        //when
        repository.remove(givenHotelId);

        //then
        assertThat(repository.isExists(givenHotelId), is(false));
    }
}
