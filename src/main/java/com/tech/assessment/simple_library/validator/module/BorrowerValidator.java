package com.tech.assessment.simple_library.validator.module;

import com.tech.assessment.simple_library.borrower.domain.BorrowerEntity;
import com.tech.assessment.simple_library.borrower.repository.BorrowerRepository;
import com.tech.assessment.simple_library.borrower.dto.RegisterBorrowerRequest;
import com.tech.assessment.simple_library.exception.ErrorDto;
import com.tech.assessment.simple_library.exception.ErrorResponseConstant;
import com.tech.assessment.simple_library.exception.custom.BorrowerEmailDuplicatedException;
import com.tech.assessment.simple_library.exception.custom.BorrowerNotFoundException;
import com.tech.assessment.simple_library.validator.rule.RuleEmailFormat;
import com.tech.assessment.simple_library.validator.rule.RuleMaxLength;
import com.tech.assessment.simple_library.validator.rule.RuleRequired;
import com.tech.assessment.simple_library.validator.ValidationResult;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BorrowerValidator implements ModuleValidator<RegisterBorrowerRequest>{
    @Override
    public ValidationResult validate(RegisterBorrowerRequest target) {
        ValidationResult result = ValidationResult.success();

        result.merge(new RuleRequired().validate("name", target.getName()));
        result.merge(new RuleMaxLength(255).validate("name", target.getName()));
        result.merge(new RuleRequired().validate("email", target.getEmail()));
        result.merge(new RuleMaxLength(255).validate("email", target.getEmail()));
        result.merge(new RuleEmailFormat().validate("email", target.getEmail()));

        return result;
    }

    public void validateEmailUniqueness(BorrowerRepository repository, String email){
        if(repository.existsByEmail(email)){
            throw new BorrowerEmailDuplicatedException();
        }
    }

    public void validateBorrowerExists(Optional<BorrowerEntity> borrowerOpt, Integer borrowerId) {
        if(borrowerOpt.isEmpty()){
            throw new BorrowerNotFoundException(String.format("Borrower id %s is not found.", borrowerId));
        }
    }
}
