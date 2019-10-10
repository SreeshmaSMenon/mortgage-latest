package com.ing.ingmortgage.service;

import com.ing.ingmortgage.dto.ProductDescription;
import com.ing.ingmortgage.dto.ProductResponse;

public interface ProductService {

	public ProductResponse getProductsByCategory(Long categoryId);

	public ProductDescription getProductDetails(Long productId);

}
