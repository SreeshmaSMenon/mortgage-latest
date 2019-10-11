package com.ing.ingmortgage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDesriptionResponse {
	private Integer succesCode;
	private String successMessage;
	private ProductDescription productDescriptionDto;

}
