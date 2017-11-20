package com.agoda.assessment.repository;

import com.agoda.assessment.repository.impl.IdRepositoryImpl;
import com.agoda.assessment.util.TestDataFactory;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class IdRepositoryTest {

    private IdRepository repository;
    private int partitionSize;

    @Before
    public void setup() {
        this.partitionSize = 5;
        this.repository = new IdRepositoryImpl(partitionSize);
        initializeStorage();
    }

    private void initializeStorage() {
        Map<Integer, Set<Integer>> givenStorage = TestDataFactory.getIdStorage(partitionSize);
        for (Integer partitionKey : givenStorage.keySet())
            this.repository.replace(partitionKey, givenStorage.get(partitionKey));
    }

    @Test
    public void doNothingTest() {
        System.out.println("Everything is OK!!!");
    }

    @Test
    public void givenNewIds_whenReplace_thenReplacedAllElementsTest() {
        //given
        int partitionKey = 1;
        Set<Integer> newIds = TestDataFactory.getIdsWith(partitionKey, new Integer[]{100, 200, 300, 400, 500});

        //when
        repository.replace(partitionKey, newIds);

        //then
        assertThat(repository.countAll(), greaterThanOrEqualTo(newIds.size()));
        for (Integer newId : newIds)
            assertThat(repository.exists(newId), is(true));

    }

    @Test
    public void givenAlreadyExistIds_whenExists_thenReturnAllTrueTest() {
        //given
        int partitionKey = 0;
        List<Integer> insertedIds = Lists.newArrayList(TestDataFactory.getIdsWith(partitionKey).iterator());

        //when
        List<Boolean> actual = repository.exists(insertedIds);

        //then
        for (Boolean exist : actual)
            assertThat(exist, is(true));
    }

    @Test
    public void givenAlreadyInsertedAndNewHotelIds_whenExists_thenReturnTrueAndFalseTest() {
        //given
        int notExistId = 99;
        int partitionKey = 0;
        List<Integer> givenIds = Lists.newArrayList(TestDataFactory.getIdsWith(partitionKey).iterator());
        givenIds.add(notExistId);

        //when
        List<Boolean> actual = repository.exists(givenIds);

        //then
        for (int i = 0; i < givenIds.size(); i++)
            assertThat(repository.exists(givenIds.get(i)), is(actual.get(i)));
    }
}
