package com.ing.ingmortgage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanResponse {
    private Integer statusCode;
    private String statusMessage;
    private CustomerCredential customerCredential;
  
}
