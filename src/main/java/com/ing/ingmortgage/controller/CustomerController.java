package com.ing.ingmortgage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ing.ingmortgage.dto.LoansResponse;
import com.ing.ingmortgage.service.CustomerService;
import com.ing.ingmortgage.util.IngMortgageUtil;

@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class CustomerController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	CustomerService customerService;
	
	@GetMapping("/customers/{customerId}/loans")
     public ResponseEntity<LoansResponse> getLoans(@PathVariable Long customerId)
     {
		LOGGER.info("inside the customer controller");
		LoansResponse loansResponse=customerService.getLoans(customerId);
		loansResponse.setStatusCode(HttpStatus.OK.value());
		loansResponse.setStatusMessage(IngMortgageUtil.SUCCESS);
		return new ResponseEntity<>(loansResponse,HttpStatus.OK);
		
     }
}
