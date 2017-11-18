package com.agoda.assessment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest
public class AgAssessmentApplicationTests {

	@Test
	public void contextLoads() {
		System.out.println("Spring Context Loading is Ok!!");
	}

}
