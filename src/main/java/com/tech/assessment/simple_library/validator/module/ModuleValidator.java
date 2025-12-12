package com.tech.assessment.simple_library.validator.module;

import com.tech.assessment.simple_library.validator.ValidationResult;

public interface ModuleValidator<T> {
    ValidationResult validate(T target);
}
