package com.ing.ingmortgage.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.ing.ingmortgage.dto.LoanDetail;

import com.ing.ingmortgage.dto.CustomerCredential;
import com.ing.ingmortgage.dto.LoanRequest;

public interface LoanService {
	public CustomerCredential applyLoan(LoanRequest loanRequest) throws NoSuchAlgorithmException;

	List<LoanDetail> getLoanDetails(Long loanId);

}
