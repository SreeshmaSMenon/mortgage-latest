package com.ing.ingmortgage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ing.ingmortgage.dto.LoanDetailsResponse;
import com.ing.ingmortgage.dto.LoansResponse;
import com.ing.ingmortgage.entity.Customer;
import com.ing.ingmortgage.entity.LoanMaster;
import com.ing.ingmortgage.exception.CommonException;
import com.ing.ingmortgage.repository.CustomerRepository;
import com.ing.ingmortgage.repository.LoanMasterRepository;
import com.ing.ingmortgage.util.IngMortgageUtil;
@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	LoanMasterRepository loanMasterRepository;
	
	@Transactional
	public LoansResponse getLoans(long customerId) {
		
		LoansResponse loansResponse=new LoansResponse();
		Optional<Customer> customer=customerRepository.findById(customerId);
		
		
		if(customer.isPresent())
		{
			loansResponse.setCif(customer.get().getCif());
			loansResponse.setCustomerName(customer.get().getFirstName()+" "+customer.get().getLastName());
			List<LoanMaster> loanMasters=loanMasterRepository.findLoanMasterByCif(customer.get().getCif());
			List<LoanDetailsResponse> loanDetailsResponses=new ArrayList<>();
			
			loanMasters.stream().forEach(
					loanMaster->
					{
					    LoanDetailsResponse loanDetailsResponse=new LoanDetailsResponse();
						loanDetailsResponse.setLoanId(loanMaster.getLoanId());
						loanDetailsResponse.setPropertyAddress(loanMaster.getPropertyStreet()+","+loanMaster.getPropertySector()+","+loanMaster.getPincode());
						loanDetailsResponses.add(loanDetailsResponse);
					});
			loansResponse.setLoanDetailsResponse(loanDetailsResponses);
			
			return loansResponse;
		}
		else
		{
			throw new CommonException(IngMortgageUtil.CUSTOMER_NOT_FOUND);
		}
		
		
	}

}
