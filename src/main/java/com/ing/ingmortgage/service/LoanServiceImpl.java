package com.ing.ingmortgage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ing.ingmortgage.dto.LoanDetail;
import com.ing.ingmortgage.entity.LoanDetails;
import com.ing.ingmortgage.entity.LoanMaster;
import com.ing.ingmortgage.exception.CommonException;
import com.ing.ingmortgage.repository.LoanRepository;
import com.ing.ingmortgage.util.IngMortgageUtil;

@Service
public class LoanServiceImpl implements LoanService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoanServiceImpl.class);

	@Autowired
	private LoanRepository loanRepository;

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

}
