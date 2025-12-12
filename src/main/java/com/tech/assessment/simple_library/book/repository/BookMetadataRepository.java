package com.tech.assessment.simple_library.book.repository;

import com.tech.assessment.simple_library.book.domain.BookMetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookMetadataRepository extends JpaRepository<BookMetadataEntity, Integer> {
    Optional<BookMetadataEntity> findByIsbn(String isbn);
}
