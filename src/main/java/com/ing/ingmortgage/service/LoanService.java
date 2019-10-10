package com.ing.ingmortgage.service;

import com.ing.ingmortgage.dto.CustomerCredential;
import com.ing.ingmortgage.dto.LoanRequest;

public interface LoanService {
  public CustomerCredential applyLoan(LoanRequest loanRequest);
}
