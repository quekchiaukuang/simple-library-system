package com.tech.assessment.simple_library.borrower.domain;

import com.tech.assessment.simple_library.borrower.dto.RegisterBorrowerRequest;
import com.tech.assessment.simple_library.borrower.dto.RegisterBorrowerResponse;
import com.tech.assessment.simple_library.borrower.repository.BorrowerRepository;
import com.tech.assessment.simple_library.exception.custom.BookMetadataException;
import com.tech.assessment.simple_library.exception.custom.BorrowerEmailDuplicatedException;
import com.tech.assessment.simple_library.validator.ValidationResult;
import com.tech.assessment.simple_library.validator.module.BorrowerValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BorrowerService {

    private static final Logger log = LoggerFactory.getLogger(BorrowerService.class);

    private final BorrowerRepository borrowerRepository;
    private final BorrowerValidator borrowerValidator;

    public BorrowerService(BorrowerRepository borrowerRepository, BorrowerValidator borrowerValidator) {
        this.borrowerRepository = borrowerRepository;
        this.borrowerValidator = borrowerValidator;
    }

    @Transactional
    public RegisterBorrowerResponse registerBorrower(RegisterBorrowerRequest request) {
        //check request format
        ValidationResult result = borrowerValidator.validate(request);
        result.throwIfFailed();

        //check email uniqueness
        borrowerValidator.validateEmailUniqueness(borrowerRepository, request.getEmail());

        try{
            //create borrower
            BorrowerEntity borrower = new BorrowerEntity();
            borrower.setName(request.getName());
            borrower.setEmail(request.getEmail());

            borrower = borrowerRepository.save(borrower);

            return new RegisterBorrowerResponse(
                    borrower.getId(),
                    borrower.getName(),
                    borrower.getEmail()
            );
        } catch (DataIntegrityViolationException ex) {
            log.error("Data integrity violation while registering borrower", ex);
            throw new BorrowerEmailDuplicatedException();
        }
    }
}
