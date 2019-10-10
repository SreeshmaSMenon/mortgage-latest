package com.ing.ingmortgage.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ing.ingmortgage.dto.LoginRequest;
import com.ing.ingmortgage.dto.LoginResponse;
import com.ing.ingmortgage.entity.Customer;
import com.ing.ingmortgage.exception.CommonException;
import com.ing.ingmortgage.repository.CustomerRepository;
import com.ing.ingmortgage.util.IngMortgageUtil;

@Service
public class LoginServiceImpl implements LoginService{

	@Autowired
	CustomerRepository customerRepository;
	
	@Transactional
	public LoginResponse login(LoginRequest loginRequest) {
		
		Customer customer=customerRepository.findByUserNameAndPassword(loginRequest.getUserName(),loginRequest.getPassword());
	   if(customer==null)
		{
			throw new CommonException(IngMortgageUtil.CUSTOMER_NOT_FOUND);
		}
		 {
		LoginResponse loginResponse=new LoginResponse();
		loginResponse.setCif(customer.getCif());
				
		return loginResponse;
		}
	}

}
