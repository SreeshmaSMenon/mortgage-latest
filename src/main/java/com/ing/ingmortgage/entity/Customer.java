package com.ing.ingmortgage.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="customer")
@Setter
@Getter
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cif;
	private String firstName;
	private String lastName;
	private String userName;
	private LocalDate dob;
	private Integer age;
	private String nationality;
	private String gender;
	private Long identificationNumber;
	private LocalDate idExpiryDate;
	@Column(unique = true)
	private Long phoneNumber;
	@Column(unique = true)
	private Long alternateNumber;
	private String employmentType;
	private String employerName;
	private String jobTitle;
	private Double totalExperience;
	private Double monthlyIncome;
	private String maritalStatus;
	@Column(unique = true)
	private String email;
	@Column(unique = true)
	private String password;	
	@OneToMany(mappedBy="customer",cascade = CascadeType.ALL)
	private List<LoanMaster> loanMasters;
}
