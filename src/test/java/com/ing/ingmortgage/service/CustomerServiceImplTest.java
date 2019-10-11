package com.ing.ingmortgage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.ing.ingmortgage.dto.LoanDetailsResponse;
import com.ing.ingmortgage.dto.LoansResponse;
import com.ing.ingmortgage.entity.Customer;
import com.ing.ingmortgage.entity.LoanMaster;
import com.ing.ingmortgage.exception.CommonException;
import com.ing.ingmortgage.repository.CustomerRepository;
import com.ing.ingmortgage.repository.LoanMasterRepository;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest{
	
 @InjectMocks
 CustomerServiceImpl customerServiceImpl;
 
 @Mock
 CustomerRepository customerRepository;
	@Mock
	LoanMasterRepository loanMasterRepository;
	
	LoansResponse loansResponse;
	long customerid=1L;
	Customer customer;
	List<LoanMaster> loanMasters;
	List<LoanDetailsResponse> loanDetailsResponses;
	 LoanDetailsResponse loanDetailsResponse;
	 LoanMaster  loanMaster;
	
	@Before
	public void setup()
	{
		customer=new Customer();
		customer.setCif(1L);
		loansResponse=new LoansResponse();
		loansResponse.setCif(1L);
		
		loanMaster=new LoanMaster();
		
		loanMasters=new ArrayList<>();
		loanMasters.add(loanMaster);
		loanDetailsResponse=new LoanDetailsResponse();
		
		loanDetailsResponses=new ArrayList<>();
		loanDetailsResponses.add(loanDetailsResponse);
		
	}
	
	@Test
	public void testGetLoans()
	{
		Mockito.when(customerRepository.findById(customerid)).thenReturn(Optional.of(customer));
		Mockito.when(loanMasterRepository.findLoanMasterByCif(customerid)).thenReturn(loanMasters);
		
		LoansResponse actual=customerServiceImpl.getLoans(customerid);
		Assert.assertEquals(loansResponse.getCif(),actual.getCif());
	}
	@Test(expected = CommonException.class)
	public void testGetLoans1()
	{
		Mockito.when(customerRepository.findById(customerid)).thenReturn(Optional.empty());
		
		loansResponse=customerServiceImpl.getLoans(customerid);
		
	}
	

}
