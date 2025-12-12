package com.tech.assessment.simple_library.exception.custom;

import lombok.Getter;

@Getter
public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException() {
        super("Book is not found.");
    }

    public BookNotFoundException(String message) {
        super(message);
    }
}
