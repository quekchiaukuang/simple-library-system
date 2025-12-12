package com.tech.assessment.simple_library.book.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book_metadata")
@Data
@NoArgsConstructor
public class BookMetadataEntity {
    @Id
    @Column(length = 32)
    private String isbn;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 255)
    private String author;

    @OneToMany(mappedBy = "bookMetadataEntity", cascade = CascadeType.ALL)
    private List<BookEntity> copies = new ArrayList<>();
}
