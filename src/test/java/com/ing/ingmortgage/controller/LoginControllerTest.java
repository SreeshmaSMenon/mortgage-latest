package com.ing.ingmortgage.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ing.ingmortgage.dto.LoginRequest;
import com.ing.ingmortgage.dto.LoginResponse;
import com.ing.ingmortgage.service.LoginService;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {
	
	@InjectMocks
	LoginController loginController;
	
	@Mock
	LoginService loginService;
	
	LoginRequest loginRequest;
	
	LoginResponse loginResponse;
	
	@Before
	public void setup() {
		loginRequest=new LoginRequest();
		loginRequest.setUserName("kiruthika");
		loginRequest.setPassword("joyce");
		loginResponse=new LoginResponse();
		loginResponse.setCif(1L);
		loginResponse.setStatusCode(200);
		
	}
	
	@Test
	public void testLogin()
	{
		Mockito.when(loginService.login(loginRequest)).thenReturn(loginResponse);
		
		ResponseEntity<LoginResponse> actual=loginController.login(loginRequest);
		
		ResponseEntity<LoginResponse> expected=new ResponseEntity<>(loginResponse,HttpStatus.OK);
      
		assertEquals(expected.getStatusCode().value(), actual.getStatusCodeValue());
	}

}
