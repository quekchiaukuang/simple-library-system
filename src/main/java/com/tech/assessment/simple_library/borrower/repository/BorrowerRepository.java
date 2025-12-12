package com.tech.assessment.simple_library.borrower.repository;

import com.tech.assessment.simple_library.borrower.domain.BorrowerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowerRepository extends JpaRepository<BorrowerEntity, Integer> {

    boolean existsByEmail(String email);
}
