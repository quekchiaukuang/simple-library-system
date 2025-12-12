package com.tech.assessment.simple_library.book.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Integer id;
    private String isbn;
    private String title;
    private String author;
    private String availableStatus;
    @JsonProperty(value = "borrower_by")
    private RegisterBookBorrowerDto borrowerBy;
}
