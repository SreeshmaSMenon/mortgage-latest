package com.ing.ingmortgage.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ing.ingmortgage.dto.CategoryDetails;
import com.ing.ingmortgage.dto.CategoryResponse;
import com.ing.ingmortgage.dto.UploadResponse;
import com.ing.ingmortgage.service.CategoryService;
/**
 * @since 2019-10-03
 * This class includes methods for upload categories and retrieving all the categories.
 */
@RestController
@RequestMapping("/categories")
@CrossOrigin(allowedHeaders = {"*","*/"}, origins = {"*","*/"})
public class CategoryController {
    private static Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	CategoryService categoryService;
	/**
	 * @return ResponseEntity of CategoryResponse which includes all the categories along with their Ids
	 * This method will collect all the category objects as list and populate CategoryResponse 
	 */
	@GetMapping("/")
	public ResponseEntity<CategoryResponse> getAllCategories() {
		logger.info("getAllCategories() in  CategoryController started");
		CategoryResponse response =new CategoryResponse();
		response.setStatusCode(200);
		response.setStatusMessage("Success");
		List<CategoryDetails> categoryList=categoryService.getAllCategories();
		response.setCategories(categoryList);
		logger.info("getAllCategories() in  CategoryController ended");
	    return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	/**
	 * @param fileInput is a type of MultipartFile
	 * @return ResponseEntity of CategoryResponse which includes all the categories along with their Ids
	 * This method will accept multipartFile as a input and call the upload method in service layerand
	 * populate UploadResponse
	 */
	@PostMapping("/")
	public ResponseEntity<UploadResponse> upload(@RequestParam("file") MultipartFile fileInput) {
		logger.info("getAllCategories() in  CategoryController started");
		UploadResponse uploadResponse=new UploadResponse();
		categoryService.upload(fileInput);
		uploadResponse.setStatusCode(200);
		uploadResponse.setMessage("success");
		logger.info("getAllCategories() in  CategoryController ended");
	    return new ResponseEntity<>(uploadResponse,HttpStatus.OK);
	}
}
