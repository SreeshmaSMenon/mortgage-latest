package com.ing.ingmortgage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ing.ingmortgage.entity.LoanMaster;

@Repository
public interface LoanMasterRepository extends JpaRepository<LoanMaster, Long>{

	@Query("select L from LoanMaster L where L.customer.cif = :cif")
	public List<LoanMaster> findLoanMasterByCif(Long cif);

	

}
