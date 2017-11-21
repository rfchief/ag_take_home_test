package com.agoda.assessment.repository;

import com.agoda.assessment.util.TestDataFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest
public class IdRepositoryIntegrationTest {

    @Autowired
    @Qualifier("hotelIdRepository")
    private IdRepository hotelIdRepository;

    @Test
    public void doNothingTest() {
        System.out.println("Spring Context Loading is OK!!!");
    }

    @Test
    public void givenHotelIdsWithPartitionKey_whenReplace_thenReplacesPartitionedStorageTest() {
        //given
        int partitionKey = 1;
        Set<Integer> hotelIds = TestDataFactory.getIdsWith(partitionKey);

        //when
        hotelIdRepository.replace(partitionKey, hotelIds);

        //then
        for (Integer hotelId : hotelIds)
            assertThat(hotelIdRepository.exists(hotelId), is(true));
    }

}
