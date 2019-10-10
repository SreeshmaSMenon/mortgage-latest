package com.ing.ingmortgage.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoanRequest {
	private String title;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	private LocalDate dob;
	@NotNull
	private int age;
	@NotNull
	private String nationality;
	@NotNull
	private String gender;
	@NotNull
	private Long identificationNumber;
	@NotNull
	private LocalDate idExpiryDate;
	@NotNull
	private Long phoneNumber;
	private Long alternateNumber;
	@NotNull
	private String employmentType;
	@NotNull
	private String employerName;
	@NotNull
	private String jobTitle;
	@NotNull
	private Double totalExperience;
	@NotNull
	private Double monthlyIncome;
	@NotNull
	private String maritalStatus;
	@NotNull
	private Double loanObligation;
	@NotNull
	private Double purchasePrice;
	@NotNull
	private Double loanAmount;
	@NotNull
	private Double loanToValue;
	@NotNull
	private Integer tenure;
	@NotNull
	private Double downPayment;
	@NotNull
	private String propertyStreet;
	@NotNull
	private String propertySector;
	@NotNull
	private Long pincode;
	@NotNull
	private String email;
}
