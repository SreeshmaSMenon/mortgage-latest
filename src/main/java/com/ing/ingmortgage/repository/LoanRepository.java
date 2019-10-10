package com.ing.ingmortgage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ing.ingmortgage.entity.LoanMaster;

@Repository
public interface LoanRepository extends JpaRepository<LoanMaster, Long> {

}
