package com.ing.ingmortgage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ing.ingmortgage.entity.Account;
import com.ing.ingmortgage.entity.Customer;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
  public List<Account> findByCustomer(Customer customer);
}
