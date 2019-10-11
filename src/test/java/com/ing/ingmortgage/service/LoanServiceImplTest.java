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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ing.ingmortgage.dto.LoanDetail;
import com.ing.ingmortgage.dto.LoanDetailResponse;
import com.ing.ingmortgage.entity.Customer;
import com.ing.ingmortgage.entity.LoanDetails;
import com.ing.ingmortgage.entity.LoanMaster;
import com.ing.ingmortgage.repository.LoanRepository;

@RunWith(MockitoJUnitRunner.class)
public class LoanServiceImplTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoanServiceImpl.class);

	@InjectMocks
	private LoanServiceImpl loanServiceImpl;

	@Mock
	private LoanRepository loanRepository;

	LoanDetailResponse loanDetailResponse;

	List<LoanDetail> loanDetailList = new ArrayList<>();
	LoanMaster loanMaster = new LoanMaster();
	List<LoanDetails> loanDetailsList = new ArrayList<>();

	@Before
	public void setup() {
		loanDetailResponse = new LoanDetailResponse();

		Customer customer = new Customer();

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

		loanDetailsList.add(loanDetails);
		loanMaster.setLoanDetails(loanDetailsList);
	}

	@Test
	public void testGetLoanDetailsByIdPositive() {
		LOGGER.info("testGetLoanDetailsByIdPositive started");
		Optional<LoanMaster> optLoanMaster = Optional.of(loanMaster);
		Mockito.when(loanRepository.findByLoanIdAndLoanStatus(loanMaster.getLoanId(), "open"))
				.thenReturn(optLoanMaster);
		Mockito.when(loanRepository.findLoanDetailsByLoanId(loanMaster.getLoanId())).thenReturn(loanDetailsList);

		List<LoanDetail> actualResponse = loanServiceImpl.getLoanDetails(loanMaster.getLoanId());

		Assert.assertEquals(1, actualResponse.size());

		LOGGER.info("testGetLoanDetailsByIdPositive ended");
	}

	@Test(expected = NullPointerException.class)
	public void testGetLoanDetailsByIdNegative() {
		LOGGER.info("testGetLoanDetailsByIdNegative started");
		Optional<LoanMaster> optLoanMaster = null;
		Mockito.when(loanRepository.findByLoanIdAndLoanStatus(loanMaster.getLoanId(), "open"))
				.thenReturn(optLoanMaster);

		List<LoanDetail> actualResponse = loanServiceImpl.getLoanDetails(loanMaster.getLoanId());

		Assert.assertEquals(1, actualResponse.size());

		LOGGER.info("testGetLoanDetailsByIdNegative ended");
	}

}
