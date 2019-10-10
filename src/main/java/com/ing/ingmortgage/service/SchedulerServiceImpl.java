package com.ing.ingmortgage.service;

import java.util.List;
import java.util.Optional;
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

@Service
public class SchedulerServiceImpl implements SchedulerService{

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
	@Override
	public void updateAmountAndStatus(Long cif) {
		 Optional<Customer> optionalCustomer=customerRepository.findById(cif);
		 if(optionalCustomer.isPresent()) {
			 List<LoanMaster> loans=loanRepository.findByCustomerAndLoanStatus(optionalCustomer.get(),"open");
			 loans.forEach(loan->{
				 Account account=accountRepository.findByCustomer(optionalCustomer.get()).get(0);
				 Double accountBalance=account.getBalance();
				 Optional<LoanDetails> loanDetails=loanDetailsRepository.findByLoanMasterAndStatusOrderByPaymentDateAsc(loan,"NotPaid");
				 if(loanDetails.isPresent()) {
					 if(accountBalance>=loanDetails.get().getScheduledPayment()) {
						 loanDetailsRepository.updateStatus("paid", loan, loanDetails.get().getPaymentDate());
						 if(loanDetails.get().getEndingBalance()==0)
							 loanRepository.updateStatus("close",loan.getLoanId());
					 }
				 }
				 
			 });
		 }
	}

}
