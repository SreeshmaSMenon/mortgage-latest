package com.ing.ingmortgage.service;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ing.ingmortgage.dto.CustomerCredential;
import com.ing.ingmortgage.dto.LoanRequest;
import com.ing.ingmortgage.entity.Account;
import com.ing.ingmortgage.entity.Affordability;
import com.ing.ingmortgage.entity.Customer;
import com.ing.ingmortgage.entity.LoanDetails;
import com.ing.ingmortgage.entity.LoanMaster;
import com.ing.ingmortgage.exception.AgeException;
import com.ing.ingmortgage.exception.CommonException;
import com.ing.ingmortgage.exception.EmailException;
import com.ing.ingmortgage.repository.AffordabilityRepository;
import com.ing.ingmortgage.repository.CustomerRepository;
import com.ing.ingmortgage.util.EMICalculator;
import com.ing.ingmortgage.util.EmailValidator;
import com.ing.ingmortgage.util.IngMortgageUtil;

/**
 * @since 2019-10-10 
 * This class includes methods for apply for loan, get loan
 *        details for given loan Id
 */
@Service
public class LoanServiceImpl implements LoanService {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private AffordabilityRepository affordabilityRepository;
	private static final SecureRandom RAND = new SecureRandom();
	@Value("${password.length}")
	private String passwordLength;
	@Value("${interestRate}")
	private String interestRate;
	@Value("${initialBalance}")
	private String initialBalance;

	/**
	 * @param loanRequest
	 * @return CustomerCredential which includes cif,loan Id,success code and
	 *         success message This method will accept all loan application
	 *         details,validate,calculate scheduler and save to respective tables
	 */
	@Override
	public CustomerCredential applyLoan(LoanRequest loanRequest) {
		if(loanRequest.getAge()>50)
		  throw new AgeException(IngMortgageUtil.AGE_EXCEPTION);
		if(!new EmailValidator().validateEmail(loanRequest.getEmail()))
			throw new EmailException(IngMortgageUtil.EMAIL_EXCEPTION);
		if (!affordabilityCheck(loanRequest))
			throw new CommonException(IngMortgageUtil.NOT_AFFORDABLE_EXCEPTION);
		CustomerCredential customerCredential = new CustomerCredential();
		Customer customer = new Customer();
		LoanMaster loanMaster = new LoanMaster();
		BeanUtils.copyProperties(loanRequest, customer);
		BeanUtils.copyProperties(loanRequest, loanMaster);
		customer.setUserName(customer.getFirstName()+customer.getDob().getDayOfMonth()+customer.getDob().getYear());
		Optional<String> password=generatePassword(Integer.parseInt(passwordLength));
		if(password.isPresent())
		 customer.setPassword(password.get());
		List<LoanMaster> loans = new ArrayList<>();
		loanMaster.setLoanStatus("open");
		loanMaster.setCustomer(customer);
		List<LoanDetails> loanDetails = getLoanDetails(loanRequest, loanMaster);
		List<Account> accounts=new ArrayList<>();
		Account account=new Account();
		Double acc=Math.floor(Math.random()*9_000_000_000L)+1_000_000_000;
		account.setAccountNo(acc.longValue());
		account.setBalance(Double.parseDouble(initialBalance));
		account.setCustomer(customer);
		accounts.add(account);
		customer.setAccounts(accounts);
		loanMaster.setLoanDetails(loanDetails);
		loans.add(loanMaster);
		customer.setLoanMasters(loans);
		customer = customerRepository.save(customer);
		customerCredential.setCif(customer.getCif());
	    customerCredential.setLoanId(customer.getLoanMasters().get(0).getLoanId());
		customerCredential.setUserName(customer.getUserName());
		customerCredential.setPassword(customer.getPassword());
		return customerCredential;
	}

	/**
	 * @param length
	 * @return Optional<String> which returns auto generated password.
	 */
	private static Optional<String> generatePassword(final int length) {
		if (length < 1) {
			return Optional.empty();
		}
		byte[] salt = new byte[length];
		RAND.nextBytes(salt);
		return Optional.of(Base64.getEncoder().encodeToString(salt));
	}
	/**
	 * @param loanRequest
	 * @param loanMaster
	 * @return List<LoanDetails>
	 * This method will calculate and populate loan details
	 */   
	private List<LoanDetails> getLoanDetails(LoanRequest loanRequest, LoanMaster loanMaster) {
		Double beginningBalance = loanRequest.getLoanAmount();
		LocalDate paymentDate=LocalDate.now();
		List<LoanDetails> loanDetails = new ArrayList<>();
		Integer noOfMonths = loanRequest.getTenure() * 12;
		double emi = EMICalculator.calculatedEMI(loanRequest.getLoanAmount(), Double.parseDouble(interestRate),
				loanRequest.getTenure());
		DecimalFormat df = new DecimalFormat("0.00");
		for (int i = 0; i < noOfMonths; i++) {
			LoanDetails loanDetail = new LoanDetails();
			Double monthlyInterest = (beginningBalance * (Double.parseDouble(interestRate) / 1200));
			Double emiPrincipalAmount = emi - monthlyInterest;
			Double endingBalance = beginningBalance - emiPrincipalAmount;
			loanDetail.setBeginningBalance(Double.parseDouble(df.format(beginningBalance)));
			loanDetail.setEndingBalance(Double.parseDouble(df.format(endingBalance)));
			loanDetail.setInterestAmount(Double.parseDouble(df.format(monthlyInterest)));
			loanDetail.setPaymentDate(paymentDate);
			loanDetail.setPricipalAmount(Double.parseDouble(df.format(emiPrincipalAmount)));
			loanDetail.setScheduledPayment(Double.parseDouble(df.format(emi)));
			loanDetail.setStatus("Not Paid");
			loanDetail.setLoanMaster(loanMaster);
			loanDetails.add(loanDetail);
			paymentDate=paymentDate.plusMonths(1);
			beginningBalance = endingBalance;
		}

		return loanDetails;
	}

	/**
	 * @param loanRequest
	 * @return boolean
	 * This method will check whether the customer is affordable to apply for loan
	 */   
	private boolean affordabilityCheck(LoanRequest loanRequest) {
		boolean affordabilityStatus = false;
		String maritalStatus = loanRequest.getMaritalStatus().toLowerCase();
		Double loanObligation = loanRequest.getLoanObligation();
		Double affordableAmount = 0.0;
		Optional<Affordability> optionalAffordability = affordabilityRepository.findByMaritalStatus(maritalStatus);
		if (optionalAffordability.isPresent())
			affordableAmount = optionalAffordability.get().getAffordableAmount();
		Double balanceAmount = loanRequest.getMonthlyIncome() - loanObligation - affordableAmount;
		if (Double.compare(balanceAmount, EMICalculator.calculatedEMI(loanRequest.getLoanAmount(), Double.parseDouble(interestRate),
				loanRequest.getTenure())) >= 0) {
			affordabilityStatus = true;
		}

		return affordabilityStatus;
	}
}
