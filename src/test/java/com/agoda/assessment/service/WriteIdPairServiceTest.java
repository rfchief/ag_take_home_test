package com.agoda.assessment.service;

import com.agoda.assessment.component.IdRepositoryParser;
import com.agoda.assessment.model.RequestIdPair;
import com.agoda.assessment.repository.impl.IdRepositoryImpl;
import com.agoda.assessment.util.TestDataFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class WriteIdPairServiceTest {

    private int partitionSize;
    private WriteIdPairService writeIdPairService;
    private IdRepositoryImpl hotelIdRepository;
    private IdRepositoryImpl countryIdRepository;

    @Before
    public void setup() {
        this.partitionSize = 5;
        this.hotelIdRepository = new IdRepositoryImpl(partitionSize);
        this.countryIdRepository = new IdRepositoryImpl(partitionSize);
        this.writeIdPairService = new WriteIdPairService(new IdRepositoryParser(partitionSize),
                                                        hotelIdRepository,
                                                        countryIdRepository);
    }

    @Test
    public void doNothingTest() {
        System.out.println("Everything is OK!!!");
    }

    @Test
    public void givenRequestIdPairs_whenWrite_thenReplaceHotelAndCountryIdRepositoryTest() throws IOException {
        //given
        List<RequestIdPair> requestIdPairs = TestDataFactory.getRequestIdPairs();

        //when
        writeIdPairService.write(requestIdPairs);

        //then
        for (RequestIdPair requestIdPair : requestIdPairs) {
            assertThat(hotelIdRepository.exists(requestIdPair.getHotelId()), is(true));
            assertThat(countryIdRepository.exists(requestIdPair.getCountryId()), is(true));
        }
    }
}
