package com.tech.assessment.simple_library.validator;

import com.tech.assessment.simple_library.exception.ErrorDto;
import com.tech.assessment.simple_library.exception.custom.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidationResult {
    private boolean valid;
    private List<ErrorDto> errors;

    public static ValidationResult success(){
        return new ValidationResult(true, null);
    }

    public static ValidationResult failure(ErrorDto error){
        return new ValidationResult(false, List.of(error));
    }

    public void merge(ValidationResult other){
        if(Boolean.FALSE.equals(other.isValid())) {
            if(this.errors == null) {
                this.errors = new ArrayList<>();
            }
            this.errors.addAll(other.getErrors());
            this.valid = false;
        }
    }

    public void throwIfFailed(){
        if(Boolean.FALSE.equals(this.valid)){
            throw new ValidationException(this.errors);
        }
    }
}
