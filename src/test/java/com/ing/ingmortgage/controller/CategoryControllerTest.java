package com.ing.ingmortgage.controller;

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
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import com.ing.ingmortgage.dto.CategoryDetails;
import com.ing.ingmortgage.dto.CategoryResponse;
import com.ing.ingmortgage.dto.UploadResponse;
import com.ing.ingmortgage.service.CategoryServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class CategoryControllerTest {
	 @Mock
	 CategoryServiceImpl categoryServiceImpl;
	 @InjectMocks
	 CategoryController categoryController;
	 List<CategoryDetails>details=new ArrayList<>();
	 CategoryResponse categoryResponse=new CategoryResponse();
	 MockMultipartFile firstFile;
	 UploadResponse uploadResponse;

	 @Before
	 public void setUp() throws FileNotFoundException, IOException {
		 uploadResponse=new UploadResponse();
		 CategoryDetails categoryDetails=new CategoryDetails(1L,"ProductCategory1");
		 details.add(categoryDetails);
		 categoryResponse.setStatusCode(200);
		 categoryResponse.setStatusMessage("success");
		 categoryResponse.setCategories(details);
		 ClassLoader classLoader = getClass().getClassLoader();
		 File file = new File(classLoader.getResource("Productdetails.xlsx").getFile());
		 firstFile = new MockMultipartFile("data", new FileInputStream(file));
		 uploadResponse.setStatusCode(200);
		 uploadResponse.setMessage("success");
	 }
	 @Test
	 public void testGetAllCategories() {
		 Mockito.when(categoryServiceImpl.getAllCategories()).thenReturn(details);
		 ResponseEntity<CategoryResponse> categoryResponse=categoryController.getAllCategories();
		 assertNotNull(categoryResponse);
		 
	 }
	 @Test
		public void testUpload() {
			 ResponseEntity<UploadResponse> response=categoryController.upload(firstFile);
			 assertEquals("success", response.getBody().getMessage());
		}
}
