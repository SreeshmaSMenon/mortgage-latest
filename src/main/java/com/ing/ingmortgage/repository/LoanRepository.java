package com.ing.ingmortgage.repository;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ing.ingmortgage.entity.Customer;
import com.ing.ingmortgage.entity.LoanMaster;

@Repository
public interface LoanRepository extends JpaRepository<LoanMaster, Long> {
  List<LoanMaster>findByCustomerAndLoanStatus(Customer customer,String status);
  @Modifying
	 @Transactional
	 @Query("UPDATE LoanMaster l SET l.loanStatus = :status WHERE l.loanId = :loanId")
	 int updateStatus(@Param("status") String status,@Param("loanId") Long loanId);
  
}
