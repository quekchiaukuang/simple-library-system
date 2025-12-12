package com.tech.assessment.simple_library.exception.custom;

import lombok.Getter;

@Getter
public class BookNotAvailableException extends RuntimeException{

    public BookNotAvailableException() {
        super("Book is borrowed by another user.");
    }

    public BookNotAvailableException(String message) {
        super(message);
    }
}
