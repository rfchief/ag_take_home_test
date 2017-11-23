package com.agoda.assessment.parser;

import com.agoda.assessment.enums.IdType;
import com.agoda.assessment.model.RequestIdPair;
import com.agoda.assessment.util.TestDataFactory;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class IdRepositoryParserTest {

    private IdRepositoryParser idRepositoryParser;
    private int partitionSize;

    @Before
    public void setup() {
        this.partitionSize = 5;
        this.idRepositoryParser = new IdRepositoryParser(partitionSize);
    }

    @Test
    public void doNothingTest() {
        System.out.println("Everything is Ok!!!");
    }

    @Test
    public void givenValidInputIdPairsWithIdType_whenParse_thenReturnPartitionedIdsMapTest() {
        //given
        List<RequestIdPair> idPairs = TestDataFactory.getRequestIdPairsWithExpectedSize(100);

        //when
        Map<Integer, Set<Integer>> actual = idRepositoryParser.parse(idPairs, IdType.HOTEL);

        //then
        assertThat(actual, is(notNullValue()));
        assertIdExists(idPairs, actual);
    }

    @Test
    public void givenValidAndInvalidInputIdPairsWithIdType_whenParse_thenReturnOnlyValidIdsTest() {
        //given
        List<RequestIdPair> idPairs = TestDataFactory.getRequestIdPairsWithExpectedSize(100);
        RequestIdPair invalidRequestIdPair = new RequestIdPair(-1, -1);
        List<RequestIdPair> validAndInvalidIdPairs = Lists.newArrayList(idPairs);
        validAndInvalidIdPairs.add(invalidRequestIdPair);

        //when
        Map<Integer, Set<Integer>> actual = idRepositoryParser.parse(validAndInvalidIdPairs, IdType.HOTEL);

        //then
        assertIdExists(idPairs, actual);
        for (int i = 0; i < partitionSize; i++)
            assertThat(actual.get(i).contains(invalidRequestIdPair.getIdWith(IdType.HOTEL)), is(false));
    }

    private void assertIdExists(List<RequestIdPair> idPairs, Map<Integer, Set<Integer>> actual) {
        for (RequestIdPair idPair : idPairs) {
            int id = idPair.getIdWith(IdType.HOTEL);
            int partitionKey = id % partitionSize;
            Set<Integer> partitionedIds = actual.get(partitionKey);
            assertThat(partitionedIds.contains(id), is(true));
        }
    }
}
