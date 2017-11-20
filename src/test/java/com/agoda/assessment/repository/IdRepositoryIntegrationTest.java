package com.agoda.assessment.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

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

}
