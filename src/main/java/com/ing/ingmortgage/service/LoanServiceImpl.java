package com.ing.ingmortgage.service;


import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ing.ingmortgage.dto.CustomerCredential;
import com.ing.ingmortgage.dto.LoanDetail;
import com.ing.ingmortgage.dto.LoanRequest;
import com.ing.ingmortgage.entity.Affordability;
import com.ing.ingmortgage.entity.Customer;
import com.ing.ingmortgage.entity.LoanDetails;
import com.ing.ingmortgage.entity.LoanMaster;
import com.ing.ingmortgage.exception.CommonException;
import com.ing.ingmortgage.repository.AffordabilityRepository;
import com.ing.ingmortgage.repository.CustomerRepository;
import com.ing.ingmortgage.repository.LoanRepository;
import com.ing.ingmortgage.util.IngMortgageUtil;

/**
 * @since 2019-10-10 This class includes methods for apply for loan, get loan
 *        details for given loan Id
 */
@Service
public class LoanServiceImpl implements LoanService {

	@Autowired
	private LoanRepository loanRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private AffordabilityRepository affordabilityRepository;

	private static final SecureRandom RAND = new SecureRandom();
	@Value("${password.length}")
	private String passwordLength;
	@Value("${interestRate}")
	private String interestRate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoanServiceImpl.class);


	/**
	 * @param loanId type Long
	 * @return List<LoanDetail> which returns all the loandetails related to a
	 *         particular loan chosen by user.
	 */
	@Override
	public List<LoanDetail> getLoanDetails(Long loanId) {
		LOGGER.info("getLoanDetails() Method in LoanServiceImpl started");
		List<LoanDetail> loanDetailResponseList =new ArrayList<>();
		Optional<LoanMaster> loanMaster = loanRepository.findByLoanIdAndLoanStatus(loanId, "open");
		if (loanMaster.isPresent()) {
			List<LoanDetails> loanDetailList = loanRepository.findLoanDetailsByLoanId(loanId);
			loanDetailList.forEach(loanDetail -> {
				LoanDetail loanDetailResponse = new LoanDetail();
				BeanUtils.copyProperties(loanDetail, loanDetailResponse);
				loanDetailResponseList.add(loanDetailResponse);
			});
		} else {
			throw new CommonException(IngMortgageUtil.LOAN_DETAILS_NOT_FOUND);
		}
		
		LOGGER.info("getLoanDetails() Method in LoanServiceImpl ended");
		return loanDetailResponseList;
	}
	/**
	 * @param loanRequest
	 * @return CustomerCredential which includes cif,loan Id,success code and
	 *         success message This method will accept all loan application
	 *         details,validate,calculate scheduler and save to respective tables
	 */
	@Override
	public CustomerCredential applyLoan(LoanRequest loanRequest) {
		if (!affordabilityCheck(loanRequest))
			throw new CommonException(IngMortgageUtil.NOT_AFFORDABLE_EXCEPTION);
		CustomerCredential customerCredential = new CustomerCredential();
		Customer customer = new Customer();
		LoanMaster loanMaster = new LoanMaster();
		BeanUtils.copyProperties(loanRequest, customer);
		BeanUtils.copyProperties(loanRequest, loanMaster);
		customer.setUserName(customer.getFirstName()+customer.getDob().getDayOfMonth()+customer.getDob().getYear());
		customer.setPassword(generatePassword(Integer.parseInt(passwordLength)).toString());
		List<LoanMaster> loans = new ArrayList<>();
		loanMaster.setLoanStatus("open");
		loanMaster.setCustomer(customer);
		List<LoanDetails> loanDetails = getLoanDetails(loanRequest, loanMaster);
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
	 * @since 2019-10-10
	 */
	private static Optional<String> generatePassword(final int length) {
		if (length < 1) {
			return Optional.empty();
		}
		byte[] salt = new byte[length];
		RAND.nextBytes(salt);
		return Optional.of(Base64.getEncoder().encodeToString(salt));
	}

	private List<LoanDetails> getLoanDetails(LoanRequest loanRequest, LoanMaster loanMaster) {
		Double beginningBalance = loanRequest.getLoanAmount();
		List<LoanDetails> loanDetails = new ArrayList<>();
		Integer noOfMonths = loanRequest.getTenure() * 12;
		double emi = emiCalculator(loanRequest.getLoanAmount(), Double.parseDouble(interestRate),
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
			loanDetail.setPaymentDate(LocalDate.now());
			loanDetail.setPrincipalAmount(Double.parseDouble(df.format(emiPrincipalAmount)));
			loanDetail.setScheduledPayment(Double.parseDouble(df.format(emi)));
			loanDetail.setStatus("Not Paid");
			loanDetail.setLoanMaster(loanMaster);
			beginningBalance = endingBalance;
			loanDetails.add(loanDetail);
		}

		return loanDetails;
	}

	private Double emiCalculator(Double principal, Double rateOfInterest, Integer tenure) {
		Double emi;

		rateOfInterest = rateOfInterest / (12 * 100); // one month interest
		tenure = tenure * 12; // one month period
		emi = (principal * rateOfInterest * (Double) Math.pow(1 + rateOfInterest, tenure))
				/ (Double) (Math.pow(1 + rateOfInterest, tenure) - 1);

		return (emi);
	}

	private boolean affordabilityCheck(LoanRequest loanRequest) {
		boolean affordabilityStatus = false;
		String maritalStatus = loanRequest.getMaritalStatus().toLowerCase();
		Double loanObligation = loanRequest.getLoanObligation();
		Double affordableAmount = 0.0;
		Optional<Affordability> optionalAffordability = affordabilityRepository.findByMaritalStatus(maritalStatus);
		if (optionalAffordability.isPresent())
			affordableAmount = optionalAffordability.get().getAffordableAmount();
		Double balanceAmount = loanRequest.getMonthlyIncome() - loanObligation - affordableAmount;
		if (Double.compare(balanceAmount, emiCalculator(loanRequest.getLoanAmount(), Double.parseDouble(interestRate),
				loanRequest.getTenure())) >= 0) {
			affordabilityStatus = true;
		}

		return affordabilityStatus;
	}
}
