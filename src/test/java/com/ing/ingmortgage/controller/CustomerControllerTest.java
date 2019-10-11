package com.ing.ingmortgage.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ing.ingmortgage.controller.CustomerController;
import com.ing.ingmortgage.dto.LoansResponse;
import com.ing.ingmortgage.service.CustomerService;

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class CustomerControllerTest {
	
	@InjectMocks
	CustomerController customerController;
	
	@Mock
	CustomerService customerService;
	Long customerId=1L;
	LoansResponse loansResponse;
	
	@Before
	public void setup() {
		loansResponse=new LoansResponse();
		loansResponse.setCif(1L);
		loansResponse.setStatusCode(200);
	}
	
	@Test
	public void testGetLoans()
	{
		Mockito.when(customerService.getLoans(customerId)).thenReturn(loansResponse);
		ResponseEntity<LoansResponse> actual=customerController.getLoans(customerId);
		ResponseEntity<LoansResponse> expected=new ResponseEntity<>(loansResponse,HttpStatus.OK);
		
		assertEquals(expected.getStatusCode().value(), actual.getStatusCodeValue());

	}
	
	}


