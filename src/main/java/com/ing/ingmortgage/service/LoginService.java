package com.ing.ingmortgage.service;

import com.ing.ingmortgage.dto.LoginRequest;
import com.ing.ingmortgage.dto.LoginResponse;

public interface LoginService {
	
	public LoginResponse login (LoginRequest loginRequest);

}
