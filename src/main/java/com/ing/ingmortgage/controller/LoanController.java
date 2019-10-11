package com.ing.ingmortgage.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ing.ingmortgage.dto.CustomerCredential;
import com.ing.ingmortgage.dto.LoanDetail;
import com.ing.ingmortgage.dto.LoanDetailResponse;
import com.ing.ingmortgage.dto.LoanRequest;
import com.ing.ingmortgage.dto.LoanResponse;
import com.ing.ingmortgage.service.LoanService;
import com.ing.ingmortgage.util.IngMortgageUtil;

/**
 * @since 2019-10-10 This class includes methods for apply for loan, get loan
 *        details for given loan Id
 */
@RestController
@RequestMapping("/loans")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class LoanController {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoanController.class);
	@Autowired
	private LoanService loanService;

	/**
	 * 
	 * @param loanId type Long
	 * @return ResponseEntity of LoanDetailsResponse which includes loan detail
	 *         description for given loan Id. This method will get description for
	 *         given loan ID and populate LoanDetailResponse
	 */
	@GetMapping("/{loanId}")
	public ResponseEntity<LoanDetailResponse> loanDetails(@PathVariable Long loanId) {
		LOGGER.info("loanDetails() Method in LoanController started");
		LoanDetailResponse loanDetailResponse = new LoanDetailResponse();
		List<LoanDetail> loanDescription = loanService.getLoanDetails(loanId);
		loanDetailResponse.setLoanDetail(loanDescription);
		loanDetailResponse.setStatusCode(200);
		loanDetailResponse.setStatusMessage(IngMortgageUtil.SUCCESS);
		LOGGER.info("loanDetails() Method in LoanController ended");
		return new ResponseEntity<>(loanDetailResponse, HttpStatus.OK);
	}

	/**
	 * @param loanRequest
	 * @return ResponseEntity of LoanResponse which includes cif,loan Id,success
	 *         code and success message This method will accept all loan application
	 *         details,validate,calculate scheduler and save to respective tables
	 */
	@PostMapping("/")
	public ResponseEntity<LoanResponse> applyLoan(@Valid @RequestBody LoanRequest loanRequest)throws NoSuchAlgorithmException {
		LOGGER.info("applyLoan() in  LoanController started");
		LoanResponse loanResponse = new LoanResponse();
		CustomerCredential credentials = loanService.applyLoan(loanRequest);
		loanResponse.setStatusCode(200);
		loanResponse.setStatusMessage("Success");
		loanResponse.setCustomerCredential(credentials);
		LOGGER.info("applyLoan() in  LoanController ended");
		return new ResponseEntity<>(loanResponse, HttpStatus.CREATED);
	}
}
