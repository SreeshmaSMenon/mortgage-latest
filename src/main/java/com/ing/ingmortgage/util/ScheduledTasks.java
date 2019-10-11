package com.ing.ingmortgage.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.ing.ingmortgage.service.SchedulerService;
@Component
public class ScheduledTasks {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);
    @Autowired
	SchedulerService schedulerService;
    @Value("${cif}")
	private String cif;

    
	//@Scheduled(fixedRate = 5000)
	public void run() {
		LOGGER.info("run() in  ScheduledTasks ended");
		schedulerService.updateAmountAndStatus(Long.valueOf(cif));
		LOGGER.info("run() in  ScheduledTasks ended");

	}
}
