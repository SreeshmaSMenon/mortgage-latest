package com.ing.ingmortgage.entity;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="loan_master")
@Setter
@Getter
public class LoanMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long loanId;
	private Double loanObligation;
	private Double purchasePrice;
	private Double loanAmount;
	private Double loanToValue;
	private Double downPayment;
	private String loanStatus;
	private String propertyStreet;
	private String propertySector;
	private Long pincode;
	private Integer tenure;
	@ManyToOne
    @JoinColumn(name="cif", nullable=false)
	private Customer customer;
	@OneToMany(mappedBy="loanMaster",cascade = CascadeType.ALL)
	private List<LoanDetails> loanDetails;

}
