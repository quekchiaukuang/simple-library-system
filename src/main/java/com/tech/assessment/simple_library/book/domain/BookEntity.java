package com.tech.assessment.simple_library.book.domain;

import com.tech.assessment.simple_library.borrower.domain.BorrowerEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "isbn", nullable = false)
    private BookMetadataEntity bookMetadataEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currently_borrowed_by")
    private BorrowerEntity currentlyBorrowedBy;

    @OneToMany(mappedBy = "bookEntity")
    private List<BorrowRecordEntity> borrowHistory = new ArrayList<>();
}
