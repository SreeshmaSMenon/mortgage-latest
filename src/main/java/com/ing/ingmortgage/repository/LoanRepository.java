package com.ing.ingmortgage.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ing.ingmortgage.entity.LoanDetails;
import com.ing.ingmortgage.entity.LoanMaster;

@Repository
public interface LoanRepository extends JpaRepository<LoanMaster, Long> {

	@Query("select l from LoanDetails l where l.loanMaster.loanId = :loanId")
	List<LoanDetails> findLoanDetailsByLoanId(Long loanId);

	Optional<LoanMaster> findByLoanIdAndLoanStatus(Long loanId, String loanStatus);
}
