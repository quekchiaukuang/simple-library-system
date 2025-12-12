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
public class ReturnBookResponse {
    @JsonProperty(value = "book_id")
    private Integer bookId;
    @JsonProperty(value = "borrower_id")
    private Integer borrowerId;
    @JsonProperty(value = "returned_at")
    private String returnedAt;
}
