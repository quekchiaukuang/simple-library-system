package com.tech.assessment.simple_library.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterBookRequest {
    private String isbn;
    private String title;
    private String author;
}
