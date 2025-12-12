package com.tech.assessment.simple_library.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterBookBorrowerDto {
    private Integer id;
    private String name;
    private String email;
}
