package com.ing.ingmortgage.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ing.ingmortgage.dto.LoanDetail;
import com.ing.ingmortgage.dto.LoanDetailResponse;
import com.ing.ingmortgage.entity.Customer;
import com.ing.ingmortgage.entity.LoanDetails;
import com.ing.ingmortgage.entity.LoanMaster;
import com.ing.ingmortgage.service.LoanService;

@RunWith(MockitoJUnitRunner.class)
public class LoanControllerTest {

	@InjectMocks
	LoanController loanController;

	@Mock
	LoanService loanService;

	LoanDetailResponse loanDetailResponse;

	List<LoanDetail> loanDetailList = new ArrayList<>();
	LoanMaster loanMaster = new LoanMaster();
	
	@Before
	public void setup() {
		loanDetailResponse = new LoanDetailResponse();

			
		Customer customer=new Customer();
		
		loanMaster.setCustomer(customer);
		loanMaster.setLoanAmount(400000.00);
		loanMaster.setDownPayment(20000.00);
		loanMaster.setLoanId(1L);
		loanMaster.setPurchasePrice(5000000.00);
		loanMaster.setPropertyStreet("PK street");
		loanMaster.setLoanObligation(300000.00);
		loanMaster.setPincode(600028L);
		loanMaster.setPropertySector("Mandaveli");
		
		LoanDetails loanDetails = new LoanDetails();
		loanDetails.setLoanMaster(loanMaster);
		loanDetails.setBeginningBalance(20000.00);
		loanDetails.setEndingBalance(0.0);
		loanDetails.setInterestAmount(40000.00);
		loanDetails.setStatus("open");
		
		List<LoanDetails> loanDetailsList=new ArrayList<>();
		loanDetailsList.add(loanDetails);
		loanMaster.setLoanDetails(loanDetailsList);

	}

	@Test
	public void testLoanDetails() {
		Mockito.when(loanService.getLoanDetails(Mockito.anyLong())).thenReturn(loanDetailList);
		ResponseEntity<LoanDetailResponse> actual = loanController.loanDetails(loanMaster.getLoanId());
		ResponseEntity<LoanDetailResponse> expected = new ResponseEntity<LoanDetailResponse>(loanDetailResponse,
				HttpStatus.OK);
		assertEquals(expected.getStatusCode().value(), actual.getStatusCodeValue());
	}

}
