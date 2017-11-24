package com.agoda.assessment.service;

import com.agoda.assessment.parser.IdRepositoryParser;
import com.agoda.assessment.model.HotelIdScore;
import com.agoda.assessment.model.RequestIdPair;
import com.agoda.assessment.repository.ConfigRepository;
import com.agoda.assessment.repository.impl.IdRepositoryImpl;
import com.agoda.assessment.strategy.BasicCalculateStrategy;
import com.agoda.assessment.util.TestDataFactory;
import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReadIdPairServiceTest {

    private int partitionSize;
    private ReadIdPairService readIdService;
    private IdRepositoryImpl hotelIdRepository;
    private IdRepositoryImpl countryIdRepository;
    private List<RequestIdPair> insertedRequestIdPairs;
    private ConfigRepository configRepository;
    private BasicCalculateStrategy calculateStrategy;

    @Before
    public void setup() throws IOException {
        initFieldVariables();
        this.readIdService = new ReadIdPairService(hotelIdRepository, countryIdRepository, configRepository, calculateStrategy);
    }

    private void initFieldVariables() throws IOException {
        this.partitionSize = 5;
        this.insertedRequestIdPairs = TestDataFactory.getRequestIdPairs();
        this.hotelIdRepository = new IdRepositoryImpl(partitionSize);
        this.countryIdRepository = new IdRepositoryImpl(partitionSize);
        this.configRepository = new ConfigRepository(Maps.newHashMap());
        this.calculateStrategy = new BasicCalculateStrategy();
        WriteIdPairService writeIdPairService = new WriteIdPairService(new IdRepositoryParser(partitionSize),
                                                                        hotelIdRepository,
                                                                        countryIdRepository);
        writeIdPairService.write(insertedRequestIdPairs);
    }

    @Test
    public void doNothingTest() {
        System.out.println("Everything is Ok!!");
    }

    @Test
    public void givenRequestIdPairs_whenRead_thenReturnHotelIdAndScoresTest() {
        //given
        List<RequestIdPair> givenRequestIdPairs = TestDataFactory.getHttpRequestIdPairs();

        //when
        List<HotelIdScore> actual = readIdService.getScores(givenRequestIdPairs);

        //then
        assertThat(actual, is(notNullValue()));

    }

}