package com.ing.ingmortgage.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="loan_details")
@Setter
@Getter
public class LoanDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long loanDetailsId;
	private Double beginningBalance;
	private Double scheduledPayment;
	private Double pricipalAmount;
	private Double interestAmount;
	private LocalDate paymentDate;
	private Double endingBalance;
	private String status;
	@ManyToOne
    @JoinColumn(name="loanId", nullable=false)
	private LoanMaster loanMaster;
	

}
