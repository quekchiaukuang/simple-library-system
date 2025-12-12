package com.tech.assessment.simple_library.exception.custom;

import com.tech.assessment.simple_library.exception.ErrorDto;
import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException{

    private final List<ErrorDto> errors;

    public ValidationException(List<ErrorDto> errors) {
        super("Validation failed");
        this.errors = errors;
    }
}
