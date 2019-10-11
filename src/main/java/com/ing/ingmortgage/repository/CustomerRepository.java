package com.ing.ingmortgage.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ing.ingmortgage.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

	public Customer findByUserNameAndPassword(String userName, String password);
	public Optional<Customer> findByUserName(String username);
	public Optional<Customer> findByEmailOrPhoneNumber(String email,Long phoneNumber);

}
