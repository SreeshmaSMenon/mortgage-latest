package com.ing.ingmortgage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.ing.ingmortgage.dto.ProductDescription;
import com.ing.ingmortgage.dto.ProductDesriptionResponse;
import com.ing.ingmortgage.dto.ProductResponse;
import com.ing.ingmortgage.service.ProductService;
import com.ing.ingmortgage.util.IngMortgageUtil;


/**
 * @since 2019-10-03
 * This class includes methods to get products for given category ID and product description for given product ID.
 */
@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class ProductController {
	@Autowired
	ProductService productServiceIntf;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
	/**
	 * @param categoryId type Long
	 * @return ResponseEntity of ProductResponse which includes all the products for given category Id.
	 * This method will collect all the Products objects as list and populate CategoryResponse 
	 */
	@GetMapping("/categories/{categoryId}/products")
	public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId)
	{
		LOGGER.info("inside product Controller");
		ProductResponse productResponse=productServiceIntf.getProductsByCategory(categoryId);
		
		return new ResponseEntity<>(productResponse,HttpStatus.OK);
		
	}
	/**
	 * @param productId type Long
	 * @return ResponseEntity of ProductDesriptionResponse which includes product description for given product Id.
	 * This method will get description for given product ID and populate ProductDesriptionResponse 
	 */
	
	@GetMapping("/products/{productId}")
	public ResponseEntity<ProductDesriptionResponse> getProductDetails(@PathVariable Long productId)
	{
		LOGGER.info("inside product Description");
		ProductDesriptionResponse productDesriptionResponse=new ProductDesriptionResponse();
		ProductDescription productDescription=productServiceIntf.getProductDetails(productId);
		productDesriptionResponse.setProductDescriptionDto(productDescription);
		productDesriptionResponse.setSuccesCode(200);
		productDesriptionResponse.setSuccessMessage(IngMortgageUtil.SUCCESS);
		return new ResponseEntity<>(productDesriptionResponse,HttpStatus.OK);
		
	}

}
