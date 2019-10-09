package com.ing.ingmortgage.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="category")
@Setter
@Getter
public class Category {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long categoryId;
	 private String categoryName;
	 @OneToMany(mappedBy="category",cascade = CascadeType.ALL)
	 private List<Product> products;
}
