package com.ing.ingmortgage.service;

import java.util.List;

import com.ing.ingmortgage.dto.LoanDetail;

public interface LoanService {

	List<LoanDetail> getLoanDetails(Long loanId);

}
