package com.tech.assessment.simple_library.borrower.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterBorrowerResponse {
    private Integer id;
    private String name;
    private String email;
}
