package com.ing.ingmortgage.service;

import com.ing.ingmortgage.dto.ProductDescriptionDto;
import com.ing.ingmortgage.dto.ProductResponse;

public interface ProductService {

	public ProductResponse getProductsByCategory(Long categoryId);

	public ProductDescriptionDto getProductDetails(Long productId);

}
