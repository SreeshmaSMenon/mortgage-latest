package com.ing.ingmortgage.service;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import com.ing.ingmortgage.dto.LoginRequest;
import com.ing.ingmortgage.dto.LoginResponse;
import com.ing.ingmortgage.entity.Customer;
import com.ing.ingmortgage.exception.CommonException;
import com.ing.ingmortgage.repository.CustomerRepository;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceImplTest {
	
	@InjectMocks
	LoginServiceImpl loginServiceImpl;
	
	@Mock
	CustomerRepository customerRepository;
	
	
    LoginRequest loginRequest;
	
	LoginResponse loginResponse;
	
	Customer customer;
	
	@Before
	public void setup() {
		loginRequest=new LoginRequest();
		loginRequest.setUserName("kiruthika");
		loginRequest.setPassword("joyce");
		loginResponse=new LoginResponse();
		loginResponse.setCif(1L);
		loginResponse.setStatusCode(200);
		
		customer=new Customer();
		customer.setCif(1L);
	}
	
	@Test
	public void testLogin()
	{
		Mockito.when(customerRepository.findByUserNameAndPassword(Mockito.anyString(), Mockito.anyString())).thenReturn(customer);
		LoginResponse actual=loginServiceImpl.login(loginRequest);
		Assert.assertEquals(loginResponse.getCif(), actual.getCif());
	
	}
	
	@Test(expected = CommonException.class)
	public void testLogin1()
	{
		Customer customer=null;
		Mockito.when(customerRepository.findByUserNameAndPassword(Mockito.anyString(), Mockito.anyString())).thenReturn(customer);
		LoginResponse actual=loginServiceImpl.login(loginRequest);
		Assert.assertEquals(loginResponse.getCif(), actual.getCif());
	
	}

}
