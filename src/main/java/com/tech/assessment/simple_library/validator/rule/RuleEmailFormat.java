package com.tech.assessment.simple_library.validator.rule;

import com.tech.assessment.simple_library.exception.ErrorDto;
import com.tech.assessment.simple_library.exception.ErrorResponseConstant;
import com.tech.assessment.simple_library.validator.ValidationResult;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class RuleEmailFormat implements ValidationRule<String>{

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    @Override
    public ValidationResult validate(String fieldName, String email) {
        if(null == email || email.isBlank()){
            return ValidationResult.success();
        }

        if(!EMAIL_PATTERN.matcher(email).matches()){
            return ValidationResult.failure(
                    ErrorDto.builder()
                            .code(ErrorResponseConstant.CODE.INVALID)
                            .message("Email format is invalid")
                            .source(fieldName).build()
            );
        }
        return ValidationResult.success();
    }
}
