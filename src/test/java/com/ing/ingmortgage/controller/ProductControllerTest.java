package com.ing.ingmortgage.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ing.ingmortgage.dto.ProductDescription;
import com.ing.ingmortgage.dto.ProductDesriptionResponse;
import com.ing.ingmortgage.dto.ProductResponse;
import com.ing.ingmortgage.entity.Category;
import com.ing.ingmortgage.entity.Product;
import com.ing.ingmortgage.service.ProductService;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {
	
	@InjectMocks
	ProductController productController;
	
	@Mock
	ProductService productService;
	
	ProductResponse productResponse;
	Category category;
	Product product1;
	Product product2;
	List<Product> products;
	ProductDescription productDescription;
	
	
	@Before
	public void setup() {

	productResponse=new ProductResponse();
	
	
	product1=new Product();
	product1.setProductId(1L);
	
	product2=new Product();
	product1.setProductId(1L);
	products=new ArrayList<Product>();
	products.add(product1);
	products.add(product2);
	
	category=new Category();
	category.setCategoryId(1L);
	category.setCategoryName("bank");
	category.setProducts(products);
	
	productDescription=new ProductDescription();
	productDescription.setProductId(1L);
	
	}
	
	@Test
	public void testGetProductsByCategory()
	{
		Mockito.when(productService.getProductsByCategory(category.getCategoryId())).thenReturn(productResponse);
		
		ResponseEntity<ProductResponse> actual=productController.getProductsByCategory(category.getCategoryId());
		ResponseEntity<ProductResponse> expected=new ResponseEntity<ProductResponse>(productResponse,HttpStatus.OK);
		assertEquals(expected.getStatusCode().value(), actual.getStatusCodeValue());

	}
	
	@Test
	public void testGetProductDetails()
	{
		Mockito.when(productService.getProductDetails(product1.getProductId())).thenReturn(productDescription);
		ResponseEntity<ProductDesriptionResponse> actual=productController.getProductDetails(product1.getProductId());		
		ResponseEntity<ProductDescription> expected=new ResponseEntity<ProductDescription>(productDescription,HttpStatus.OK);
		assertEquals(expected.getStatusCode().value(), actual.getStatusCodeValue());

	}

}
