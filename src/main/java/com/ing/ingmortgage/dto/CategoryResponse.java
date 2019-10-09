package com.ing.ingmortgage.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryResponse {
	 private Integer statusCode;
	 private String statusMessage;
	 private List<CategoryDetails>categories;
}
