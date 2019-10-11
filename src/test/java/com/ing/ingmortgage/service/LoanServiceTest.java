package com.ing.ingmortgage.service;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;
import org.springframework.test.util.ReflectionTestUtils;
import com.ing.ingmortgage.dto.CustomerCredential;
import com.ing.ingmortgage.dto.LoanRequest;
import com.ing.ingmortgage.entity.Affordability;
import com.ing.ingmortgage.entity.Customer;
import com.ing.ingmortgage.entity.LoanMaster;
import com.ing.ingmortgage.exception.AffordabilityException;
import com.ing.ingmortgage.exception.AgeException;
import com.ing.ingmortgage.exception.EmailException;
import com.ing.ingmortgage.repository.AffordabilityRepository;
import com.ing.ingmortgage.repository.CustomerRepository;

@RunWith(MockitoJUnitRunner.class)
public class LoanServiceTest {
	@Mock
	private CustomerRepository customerRepository;
	@Mock
	private AffordabilityRepository affordabilityRepository;
	@InjectMocks
	LoanServiceImpl loanServiceImpl;
	LoanRequest loanRequest;
	Affordability affordability;
	Customer customer;
	@Before
	public void setup() {
		ReflectionTestUtils.setField(loanServiceImpl,"passwordLength","8");
		ReflectionTestUtils.setField(loanServiceImpl,"interestRate","8");
		ReflectionTestUtils.setField(loanServiceImpl,"initialBalance","50000");
		loanRequest=new LoanRequest();
		loanRequest.setFirstName("Sreeshma");
		loanRequest.setLastName("Menon");
		loanRequest.setPhoneNumber(98765465L);
		loanRequest.setDob(LocalDate.of(1988, 12, 13));
		loanRequest.setAge(30);
		loanRequest.setLoanAmount(3000000.0);
		loanRequest.setDownPayment(40000.0);
		loanRequest.setEmail("sree@gmail.com");
		loanRequest.setMaritalStatus("marriedwithonekid");
		loanRequest.setMonthlyIncome(50000.0);
		loanRequest.setLoanObligation(0.0);
		loanRequest.setTenure(10);
		affordability=new Affordability();
		affordability.setAffordabilityId(1L);
		affordability.setAffordableAmount(10000.0);
		affordability.setMaritalStatus("marriedwithonekid");
	    customer=new Customer();
		LoanMaster loanMaster=new LoanMaster();
		List<LoanMaster>loanMasters=new ArrayList<>();
		loanMaster.setLoanId(1L);
		BeanUtils.copyProperties(loanRequest, customer);
		BeanUtils.copyProperties(loanRequest, loanMaster);
		customer.setUserName("sree1989");
		customer.setPassword("234fgf#w");
		loanMasters.add(loanMaster);
		customer.setLoanMasters(loanMasters);
		
	}
	
	@Test
	public void testApplyLoan() {
		Optional<Affordability> affordabilityOptional=Optional.of(affordability);
		Mockito.when(customerRepository.save(Mockito.any())).thenReturn(customer);
		Mockito.when(affordabilityRepository.findByMaritalStatus(Mockito.any())).thenReturn(affordabilityOptional);
		CustomerCredential customerCredential= loanServiceImpl.applyLoan(loanRequest);
        assertNotNull(customerCredential);
	}
	
	@Test(expected = AgeException.class)
	public void testAgeException() {
		loanRequest.setAge(55);
		CustomerCredential customerCredential= loanServiceImpl.applyLoan(loanRequest);
        assertNotNull(customerCredential);
	}
	@Test(expected = EmailException.class)
	public void testEmailException() {
		loanRequest.setEmail("sree1213123");
		CustomerCredential customerCredential= loanServiceImpl.applyLoan(loanRequest);
        assertNotNull(customerCredential);
	}
	@Test(expected = AffordabilityException.class)
	public void testAffordabilityException() {
		loanRequest.setLoanObligation(20000.0);
		CustomerCredential customerCredential= loanServiceImpl.applyLoan(loanRequest);
        assertNotNull(customerCredential);
	}

}
