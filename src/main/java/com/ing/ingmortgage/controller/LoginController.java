package com.ing.ingmortgage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ing.ingmortgage.dto.LoginRequest;
import com.ing.ingmortgage.dto.LoginResponse;
import com.ing.ingmortgage.service.LoginService;
import com.ing.ingmortgage.util.IngMortgageUtil;

/**
 * @since 2019-10-10
 * This class contains the  for login
 */
@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class LoginController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	
	@Autowired
	LoginService loginService;
	
	/**
	 * @param loginRequest
	 * @return ResponseEntity of LoginResponse which includes userName and password of the customer.
	 *
	 */
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest)
	{
		LOGGER.info("inside the login controller");
	
		LoginResponse loginResponse=loginService.login(loginRequest);
		loginResponse.setStatusMessage(IngMortgageUtil.SUCCESS);
		loginResponse.setStatusCode(HttpStatus.OK.value());
		 return new ResponseEntity<>(loginResponse,HttpStatus.OK);
		
	}
	
	
	

}
