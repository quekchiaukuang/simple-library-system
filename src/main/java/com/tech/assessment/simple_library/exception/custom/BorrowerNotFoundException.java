package com.tech.assessment.simple_library.exception.custom;

import lombok.Getter;

@Getter
public class BorrowerNotFoundException extends RuntimeException{

    public BorrowerNotFoundException() {
        super("Borrower is not found.");
    }

    public BorrowerNotFoundException(String message) {
        super(message);
    }
}
