package com.agoda.assessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.agoda.assessment")
public class AgAssessmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgAssessmentApplication.class, args);
	}
}
