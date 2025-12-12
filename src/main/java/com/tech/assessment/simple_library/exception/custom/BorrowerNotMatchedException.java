package com.tech.assessment.simple_library.exception.custom;

import lombok.Getter;

@Getter
public class BorrowerNotMatchedException extends RuntimeException{

    public BorrowerNotMatchedException() {
        super("BorrowerId is not matched with book's borrower.");
    }

    public BorrowerNotMatchedException(String message) {
        super(message);
    }
}
