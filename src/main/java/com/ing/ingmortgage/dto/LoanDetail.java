package com.ing.ingmortgage.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanDetail {
	private LocalDate paymentDate;
	private Double beginningBalance;
	private Double scheduledPayment;
	private Double principalAmount;
	private Double interestAmount;
	private Double endingBalance;
	private String status;
}
