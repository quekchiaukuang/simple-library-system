package com.tech.assessment.simple_library.validator.rule;

import com.tech.assessment.simple_library.validator.ValidationResult;

public interface ValidationRule<T> {
    ValidationResult validate(String fieldName, T value);
}
