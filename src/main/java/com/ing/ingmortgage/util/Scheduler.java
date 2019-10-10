package com.ing.ingmortgage.util;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import com.ing.ingmortgage.controller.LoanController;
public class Scheduler {
    private static Logger logger = LoggerFactory.getLogger(LoanController.class);

	@Scheduled(cron = "0/2 0 0 0 0 ?")
	public void run() {
	    System.out.println("Current time is :: " + Calendar.getInstance().getTime());
	}
}
