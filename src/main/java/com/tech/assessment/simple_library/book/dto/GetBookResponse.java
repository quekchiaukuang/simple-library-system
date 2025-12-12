package com.tech.assessment.simple_library.book.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBookResponse {
    @JsonProperty(value = "total_return")
    private int totalReturn;
    private List<BookDto> books;
}
