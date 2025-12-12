package com.tech.assessment.simple_library.book.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BorrowBookRequest {
    @JsonProperty(value = "borrower_id")
    private Integer borrowerId;
}
