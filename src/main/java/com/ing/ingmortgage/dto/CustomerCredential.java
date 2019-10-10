package com.ing.ingmortgage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCredential {
	 private Long cif;
	 private Long loanId;
	 private String userName;
	 private String password;
}
