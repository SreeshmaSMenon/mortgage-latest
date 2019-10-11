package com.ing.ingmortgage.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoanDetailResponse {

	private int statusCode;
	private String statusMessage;
	private List<LoanDetail> loanDetail;

}
