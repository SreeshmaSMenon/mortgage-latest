package com.ing.ingmortgage.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ing.ingmortgage.dto.CustomerCredential;
import com.ing.ingmortgage.dto.LoanRequest;
import com.ing.ingmortgage.dto.LoanResponse;
import com.ing.ingmortgage.service.LoanService;
/**
 * @since 2019-10-10
 * This class includes methods for apply for loan, get loan details for given loan Id
 */
@RestController
@RequestMapping("/loans")
@CrossOrigin(allowedHeaders = {"*","*/"}, origins = {"*","*/"})
public class LoanController {
	
    private static Logger logger = LoggerFactory.getLogger(LoanController.class);
    @Autowired
    private LoanService loanService;

	/**
	 * @param loanRequest
	 * @return ResponseEntity of LoanResponse which includes cif,loan Id,success code and success message
	 * This method will accept all loan application details,validate,calculate scheduler and save to 
	 * respective tables
	 */
    @PostMapping("/")
	public ResponseEntity<LoanResponse>applyLoan(@Valid@RequestBody LoanRequest loanRequest){
		logger.info("applyLoan() in  LoanController started");
		LoanResponse loanResponse=new LoanResponse();
		CustomerCredential credentials=loanService.applyLoan(loanRequest);
		loanResponse.setStatusCode(200);
		loanResponse.setStatusMessage("Success");
		loanResponse.setCustomerCredential(credentials);
		logger.info("applyLoan() in  LoanController ended");
	 return new ResponseEntity<LoanResponse>(loanResponse,HttpStatus.CREATED);
	}
}
