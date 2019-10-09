package com.ing.ingmortgage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ing.ingmortgage.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	@Query("select p from Product p where p.category.categoryId = :categoryId")
	public List<Product> findProductByCategoryId(Long categoryId);


	

}
