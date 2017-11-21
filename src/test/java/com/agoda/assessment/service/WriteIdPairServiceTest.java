package com.agoda.assessment.service;

import com.agoda.assessment.component.IdRepositoryParser;
import com.agoda.assessment.repository.impl.IdRepositoryImpl;
import org.junit.Before;
import org.junit.Test;

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
}
