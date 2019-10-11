package com.ing.ingmortgage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
	
	private Long cif;
	private String statusMessage;
	private int statusCode;

}
