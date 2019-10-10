package com.ing.ingmortgage.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ing.ingmortgage.entity.Affordability;

@Repository
public interface AffordabilityRepository extends JpaRepository<Affordability, Long> {
	Optional<Affordability> findByMaritalStatus(String maritalStatus);
}
