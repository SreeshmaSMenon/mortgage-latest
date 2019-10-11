package com.ing.ingmortgage.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="account")
@Setter
@Getter
public class Account {
	@Id
	private Long accountNo;
	private Double balance;
	@ManyToOne
    @JoinColumn(name="cif", nullable=false)
	private Customer customer;
}
