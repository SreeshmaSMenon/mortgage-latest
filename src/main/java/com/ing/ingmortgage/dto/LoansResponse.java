package com.ing.ingmortgage.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoansResponse {
	
	private int statusCode;
	private String statusMessage;
	private Long cif;
	private String customerName;
	private List<LoanDetailsResponse> loanDetailsResponse;

}
