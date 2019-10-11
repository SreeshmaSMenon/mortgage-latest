package com.ing.ingmortgage.util;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ing.ingmortgage.controller.LoanController;
import com.ing.ingmortgage.service.SchedulerService;
@Component
public class ScheduledTasks {
    private static Logger logger = LoggerFactory.getLogger(LoanController.class);
    @Autowired
	SchedulerService schedulerService;
    @Value("${cif}")
	private String cif;

    
	//@Scheduled(fixedRate = 5000)
	public void run() {
		schedulerService.updateAmountAndStatus(Long.valueOf(cif));
		logger.info("Current time is :: " + Calendar.getInstance().getTime());
	}
}
