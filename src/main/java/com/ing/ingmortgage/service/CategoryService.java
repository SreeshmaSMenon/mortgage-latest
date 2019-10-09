package com.ing.ingmortgage.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import com.ing.ingmortgage.dto.CategoryDetails;

public interface CategoryService {
	 public List<CategoryDetails> getAllCategories();
	 public String upload(MultipartFile file);
}
