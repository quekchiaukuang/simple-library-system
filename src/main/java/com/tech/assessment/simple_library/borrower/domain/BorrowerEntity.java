package com.tech.assessment.simple_library.borrower.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "borrower",
        uniqueConstraints = {@UniqueConstraint(name = "uq_borrower_email", columnNames = "email")})
@Data
@NoArgsConstructor
public class BorrowerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, unique = true, length = 255)
    private String email;
}

