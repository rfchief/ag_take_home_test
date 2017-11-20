package com.agoda.assessment.repository;

import com.agoda.assessment.TestDataFactory;
import com.agoda.assessment.repository.impl.HotelRepositoryImpl;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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
    public void givenNewHotelIds_whenReplace_thenReplacedAllElementsTest() {
        //given
        Set<Integer> newHotelIds = Sets.newHashSet(90, 91, 92, 93, 94, 95, 96, 97);

        //when
        repository.replace(newHotelIds);

        //then
        assertThat(repository.countAll(), equalTo(newHotelIds.size()));
        for (Integer newHotelId : newHotelIds)
            assertThat(repository.exists(newHotelId), is(true));

    }

    @Test
    public void givenAlreadyExistHotelIds_whenExists_thenReturnAllTrueTest() {
        //given
        List<Integer> insertedHotelIds = TestDataFactory.getHotelIds();

        //when
        List<Boolean> actual = repository.exists(insertedHotelIds);

        //then
        for (Boolean exist : actual)
            assertThat(exist, is(true));
    }

    @Test
    public void givenAlreadyInsertedAndNewHotelIds_whenExists_thenReturnTrueAndFalseTest() {
        //given
        int notExistHotelId = 99;
        List<Integer> givenHotelIds = TestDataFactory.getHotelIds();
        givenHotelIds.add(notExistHotelId);

        //when
        List<Boolean> actual = repository.exists(givenHotelIds);

        //then
        for (int i = 0; i < givenHotelIds.size(); i++)
            assertThat(repository.exists(givenHotelIds.get(i)), is(actual.get(i)));
    }
}
