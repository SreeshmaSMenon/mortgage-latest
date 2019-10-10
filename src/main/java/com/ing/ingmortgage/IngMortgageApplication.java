package com.ing.ingmortgage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.ing.ingmortgage.util.Scheduler;

@SpringBootApplication
@EnableScheduling
public class IngMortgageApplication {

	public static void main(String[] args) {
		SpringApplication.run(IngMortgageApplication.class, args);
	}
	

}
