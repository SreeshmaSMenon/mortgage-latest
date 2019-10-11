package com.ing.ingmortgage.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductResponse {
	
	private String statusMessage;
	private List<ProductDetails> productDetails;
	

}
