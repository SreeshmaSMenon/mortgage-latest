package com.ing.ingmortgage.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ing.ingmortgage.entity.Account;
import com.ing.ingmortgage.entity.Customer;
import com.ing.ingmortgage.entity.LoanDetails;
import com.ing.ingmortgage.entity.LoanMaster;
import com.ing.ingmortgage.repository.AccountRepository;
import com.ing.ingmortgage.repository.CustomerRepository;
import com.ing.ingmortgage.repository.LoanDetailsRepository;
import com.ing.ingmortgage.repository.LoanRepository;

/**
 * @since 2019-10-10 This class includes methods update the loan status and
 *        amount
 */
@Service
public class SchedulerServiceImpl implements SchedulerService {
	private static Logger logger = LoggerFactory.getLogger(SchedulerServiceImpl.class);

	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	LoanRepository loanRepository;
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	LoanDetailsRepository loanDetailsRepository;
	@Autowired
	@Value("${cif}")
	private String cif;

	/**
	 * @param cif This method will update the status of loan for given cif if the
	 *            loan account has enough balance.
	 */
	@Override
	public void updateAmountAndStatus(Long cif) {
		logger.info("updateAmountAndStatus() in  SchedulerServiceImpl started");
		Optional<Customer> optionalCustomer = customerRepository.findById(cif);
		if (optionalCustomer.isPresent()) {
			List<LoanMaster> loans = loanRepository.findByCustomerAndLoanStatus(optionalCustomer.get(), "open");
			loans.forEach(loan -> {
				Account account = accountRepository.findByCustomer(optionalCustomer.get()).get(0);
				Double accountBalance = account.getBalance();
				List<LoanDetails> loanDetails = loanDetailsRepository
						.findByLoanMasterAndStatusAndPaymentDateLessThanEqual(loan, "Not Paid", LocalDate.now());
				if (loanDetails.isEmpty() && accountBalance >= loanDetails.get(0).getScheduledPayment()) {
					loanDetailsRepository.updateStatus("paid", loan, loanDetails.get(0).getPaymentDate());
					if (loanDetails.get(0).getEndingBalance() == 0)
						loanRepository.updateStatus("close", loan.getLoanId());
				}

			});
		}
		logger.info("updateAmountAndStatus() in  SchedulerServiceImpl started");
	}

}
