package com.tech.assessment.simple_library.exception.custom;

import lombok.Getter;

@Getter
public class BorrowRecordNotFoundException extends RuntimeException{

    public BorrowRecordNotFoundException() {
        super("Borrow record is not found.");
    }

    public BorrowRecordNotFoundException(String message) {
        super(message);
    }
}
