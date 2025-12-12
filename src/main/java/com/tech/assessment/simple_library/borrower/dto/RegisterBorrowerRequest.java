package com.tech.assessment.simple_library.borrower.dto;

import lombok.Data;

@Data
public class RegisterBorrowerRequest {
    private String name;
    private String email;
}
