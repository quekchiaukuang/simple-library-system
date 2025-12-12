package com.tech.assessment.simple_library.validator.rule;

import com.tech.assessment.simple_library.exception.ErrorDto;
import com.tech.assessment.simple_library.exception.ErrorResponseConstant;
import com.tech.assessment.simple_library.validator.ValidationResult;
import org.springframework.stereotype.Component;

@Component
public class RuleRequired implements ValidationRule{
    @Override
    public ValidationResult validate(String fieldName, Object value) {
        if(null == value || (value instanceof String s && s.isBlank())){
            return ValidationResult.failure(
                    ErrorDto.builder()
                            .code(ErrorResponseConstant.CODE.REQUIRED)
                            .message("Required field is missing")
                            .source(fieldName).build()
            );
        }
        return ValidationResult.success();
    }
}
