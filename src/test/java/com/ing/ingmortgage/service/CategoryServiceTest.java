package com.ing.ingmortgage.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import com.ing.ingmortgage.dto.CategoryDetails;
import com.ing.ingmortgage.entity.Category;
import com.ing.ingmortgage.entity.Product;
import com.ing.ingmortgage.repository.CategoryRepository;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {
	@Mock
	CategoryRepository categoryRepository;
	@InjectMocks
	CategoryServiceImpl categoryServiceImpl;
	Category category = new Category();
	List<Category> categories = new ArrayList<>();
	List<CategoryDetails> details = new ArrayList<>();
	MockMultipartFile firstFile;

	@Before
	public void setUp() throws FileNotFoundException, IOException {
		category.setCategoryId(1L);
		category.setCategoryName("ProductCategory1");
		categories.add(category);
		CategoryDetails categoryDetails = new CategoryDetails(1L, "ProductCategory1");
		details.add(categoryDetails);
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("Productdetails.xlsx").getFile());
		firstFile = new MockMultipartFile("data", new FileInputStream(file));
		Category category = new Category();
		category.setCategoryId(1L);
		category.setCategoryName("Category1");
		List<Product> products = new ArrayList<>();
		Product product = new Product();
		product.setProductId(1L);
		product.setProductName("Product1");
		product.setProductDescription("productDescription");
		product.setCategory(category);
		category.setProducts(products);
		categories.add(category);
	}

	@Test
	public void testGetAllCategories() {
		Mockito.when(categoryRepository.findAll()).thenReturn(categories);
		List<CategoryDetails> categoryDetailsList = categoryServiceImpl.getAllCategories();
		assertNotNull(categoryDetailsList);
	}


	@Test
	public void testUpload() {
		String message = categoryServiceImpl.upload(firstFile);
		assertEquals("success", message);
	}
}
