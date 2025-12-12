package com.tech.assessment.simple_library.book.repository;

import com.tech.assessment.simple_library.book.domain.BorrowRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecordEntity, Integer> {

    @Query("SELECT bre FROM BorrowRecordEntity bre WHERE bre.bookEntity.id=:bookId AND bre.borrower.id=:borrowerId AND bre.returnedAt IS NULL ORDER BY bre.borrowedAt DESC")
    Optional<BorrowRecordEntity> findBorrowingBook(@Param("bookId") Integer bookId, @Param("borrowerId") Integer borrowerId);
}
