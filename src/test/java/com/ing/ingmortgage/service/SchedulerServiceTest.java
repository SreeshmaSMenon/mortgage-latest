package com.ing.ingmortgage.service;

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

import com.ing.ingmortgage.entity.Account;
import com.ing.ingmortgage.entity.Customer;
import com.ing.ingmortgage.entity.LoanDetails;
import com.ing.ingmortgage.entity.LoanMaster;
import com.ing.ingmortgage.repository.AccountRepository;
import com.ing.ingmortgage.repository.CustomerRepository;
import com.ing.ingmortgage.repository.LoanDetailsRepository;
import com.ing.ingmortgage.repository.LoanRepository;

@RunWith(MockitoJUnitRunner.class)
public class SchedulerServiceTest {
	@InjectMocks
	SchedulerServiceImpl schedulerServiceImpl;

	@Mock
	CustomerRepository customerRepository;
	@Mock
	LoanRepository loanRepository;
	@Mock
	AccountRepository accountRepository;
	@Mock
	LoanDetailsRepository loanDetailsRepository;

	List<LoanMaster> loanMasters;
	LoanMaster loanMaster;
	Customer customer;
	Account account;
	LoanDetails loanDetails;
	List<LoanDetails> loanDetailsList = new ArrayList<>();
	List<Account> accountList = new ArrayList<>();

	@Before
	public void setUp() {
		customer = new Customer();
		account = new Account();
		loanMaster = new LoanMaster();
		loanMasters = new ArrayList<>();
		loanDetails = new LoanDetails();
		loanMaster.setLoanId(1L);
		loanMasters.add(loanMaster);
		customer.setCif(1L);
		customer.setUserName("sree1989");
		customer.setPassword("234fgf#w");
		customer.setLoanMasters(loanMasters);
		account.setCustomer(customer);
		account.setBalance(50000.00);
		account.setAccountNo(345678L);
		loanDetails.setScheduledPayment(40000.00);
		loanDetails.setPaymentDate(LocalDate.now());
		loanDetails.setEndingBalance(3000000.00);

		loanDetailsList.add(loanDetails);
		accountList.add(account);
	}

	@Test
	public void testUpdateAmountAndStatus() {
		Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(customer));
		Mockito.when(loanRepository.findByCustomerAndLoanStatus(Mockito.any(), "open")).thenReturn(loanMasters);
		Mockito.when(accountRepository.findByCustomer(Mockito.any())).thenReturn(accountList);
		Mockito.when(loanDetailsRepository.updateStatus("paid", Mockito.any(), Mockito.any())).thenReturn(1);
		Mockito.when(loanRepository.updateStatus("close", Mockito.anyLong())).thenReturn(1);
		schedulerServiceImpl.updateAmountAndStatus(1L);

	}
}
