package com.ing.ingmortgage.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.ing.ingmortgage.dto.CategoryDetails;
import com.ing.ingmortgage.entity.Category;
import com.ing.ingmortgage.entity.Product;
import com.ing.ingmortgage.exception.CommonException;
import com.ing.ingmortgage.repository.CategoryRepository;
import com.ing.ingmortgage.util.IngMortgageUtil;
/**
 * @since 2019-10-03
 * This class includes methods for upload categories and retrieving all the categories.
 */
@Service
public class CategoryServiceImpl implements CategoryService{
    private static Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Autowired
	CategoryRepository categoryRepository;
	String previousVal = "";


	/**
	 * @return CategoryResponse which includes all the categories along with their Ids
	 */
	@Override
	public List<CategoryDetails> getAllCategories() {
		logger.info("getAllCategories() in  CategoryServiceImpl started");
		List<Category>categories= categoryRepository.findAll();
		List<CategoryDetails> categoryList=new ArrayList<>();
		categories.forEach(category->categoryList.add(new CategoryDetails(category.getCategoryId(), category.getCategoryName())));
		logger.info("getAllCategories() in  CategoryServiceImpl ended");
		return categoryList;
	}


	/**
	 * @param file is a type of MultipartFile
	 * @return String 
	 */
	@Override
	@Transactional
	public String upload(MultipartFile file) {
		logger.info("upload() in  CategoryServiceImpl started");
		if (!file.isEmpty()) {
			try {			
				XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());   
				XSSFSheet sheet = wb.getSheetAt(0);     
				int noOfColumns = sheet.getRow(0).getPhysicalNumberOfCells();
                if(noOfColumns>3) {
                	wb.close();
                	throw new CommonException(IngMortgageUtil.EXCEL_EXCESS_COLUMN_EXCEPTION);
                }
                if(!sheet.getRow(0).getCell(0).toString().replaceAll("\\s+", "").equals("ProductCategory")||!sheet.getRow(0).getCell(1).toString().replaceAll("\\s+", "").equals("ProductName")||!sheet.getRow(0).getCell(2).toString().replaceAll("\\s+", "").equals("ProductDescription")) {
                	wb.close();
                	throw new CommonException(IngMortgageUtil.WRONG_HEADERS_EXCEPTION);
                }
				Iterator<Row> itr = sheet.iterator(); 
				itr.next();
				List<Category>categoryList=new ArrayList<>();	
				itr.forEachRemaining(row -> {
					String categoryName =row.getCell(0).toString();
					boolean exist=checkExistence(categoryList,categoryName);
                        
					if(!categoryName.equals("")&& !previousVal.replaceAll("\\s+", "").equals(categoryName.replaceAll("\\s+", "")) && !exist) {
   							 Category category = new Category();
	                         category.setCategoryName(categoryName);
	                         List<Product>productList=getProducts(sheet, category);
	                         category.setProducts(productList);
	                         categoryList.add(category);
                        
					}
    			previousVal=categoryName;

                         });
				
				categoryRepository.deleteAll();
				categoryRepository.saveAll(categoryList);
				wb.close();
			} catch (IOException e) {
				throw new CommonException(IngMortgageUtil.FILE_EXTRACT_ISSUE);
			}

		}else {
			throw new CommonException(IngMortgageUtil.FILE_EMPTY_EXCEPTION);
		}
		logger.info("upload() in  CategoryServiceImpl ended");
		return "success";

	}

	/**
	 * @param sheet
	 * @param category
	 * @return List<Product> which return List of Products extracted from excel
	 *         file.
	 */
	private List<Product> getProducts(XSSFSheet sheet, Category category) {
		List<Product> products = new ArrayList<>();
		Iterator<Row> rowiterator = sheet.iterator();
		rowiterator.next();
		rowiterator.forEachRemaining(row -> {
			if (row.getCell(0).toString().replaceAll("\\s+", "")
					.equals(category.getCategoryName().replaceAll("\\s+", ""))) {
				Product product = new Product();
				product.setProductName(row.getCell(1).toString());
				product.setProductDescription(row.getCell(2).toString());
				product.setCategory(category);
				products.add(product);
			}
		});

		return products;
	}

	/**
	 * @param categoryList
	 * @param categoryName
	 * @return boolean
	 * @since 2019-10-03
	 */
	private boolean checkExistence(List<Category> categoryList, String categoryName) {
		boolean existence = false;
		if (categoryList.stream().anyMatch(category -> category.getCategoryName().equals(categoryName)))
			existence = true;
		else
			existence = false;
		return existence;
	}
}
