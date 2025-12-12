package com.tech.assessment.simple_library.exception.custom;

import lombok.Getter;

@Getter
public class BorrowerEmailDuplicatedException extends RuntimeException{

    public BorrowerEmailDuplicatedException() {
        super("Borrower email is already registered.");
    }

    public BorrowerEmailDuplicatedException(String message) {
        super(message);
    }
}
