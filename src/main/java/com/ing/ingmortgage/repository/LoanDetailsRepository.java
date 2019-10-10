package com.ing.ingmortgage.repository;

import java.time.LocalDate;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ing.ingmortgage.entity.LoanDetails;
import com.ing.ingmortgage.entity.LoanMaster;
@Repository
public interface LoanDetailsRepository extends JpaRepository<LoanDetails,Long>{
	 Optional<LoanDetails> findByLoanMasterAndStatusOrderByPaymentDateAsc(LoanMaster loanMaster,String status);
	 @Modifying
	 @Transactional
	 @Query("UPDATE LoanDetails l SET l.status = :status WHERE l.loanMaster = :loanMaster AND l.paymentDate=:paymentDate")
	 int updateStatus(@Param("status") String status,@Param("loanMaster") LoanMaster loanMaster,@Param("paymentDate") LocalDate paymentDate);
}
