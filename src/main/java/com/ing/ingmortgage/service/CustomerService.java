package com.ing.ingmortgage.service;

import com.ing.ingmortgage.dto.LoansResponse;

public interface CustomerService {
	
	public LoansResponse getLoans(long customerId);

}
