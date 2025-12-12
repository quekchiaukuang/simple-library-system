package com.tech.assessment.simple_library.validator.rule;

import com.tech.assessment.simple_library.exception.ErrorDto;
import com.tech.assessment.simple_library.exception.ErrorResponseConstant;
import com.tech.assessment.simple_library.validator.ValidationResult;

public class RuleMaxLength implements ValidationRule<String>{

    private final int max;

    public RuleMaxLength(int max){
        this.max = max;
    }

    @Override
    public ValidationResult validate(String fieldName, String value) {
        if(null == value || value.isBlank()){
            return ValidationResult.success();
        }
        if(value.length() > this.max){
            return ValidationResult.failure(
                    ErrorDto.builder()
                            .code(ErrorResponseConstant.CODE.LENGTH)
                            .message(String.format("Maximum characters of %d is exceeded", this.max))
                            .source(fieldName).build()
            );
        }
        return ValidationResult.success();
    }
}
