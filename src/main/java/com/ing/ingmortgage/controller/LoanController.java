package com.ing.ingmortgage.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ing.ingmortgage.dto.LoanDetail;
import com.ing.ingmortgage.dto.LoanDetailResponse;
import com.ing.ingmortgage.service.LoanService;
import com.ing.ingmortgage.util.IngMortgageUtil;

@RestController
@RequestMapping("/loans")
public class LoanController {

	@Autowired
	LoanService loanService;

	private static final Logger LOGGER = LoggerFactory.getLogger(LoanController.class);

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

}
