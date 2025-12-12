package com.tech.assessment.simple_library.exception.custom;

import lombok.Getter;

@Getter
public class BookNotBorrowedException extends RuntimeException{

    public BookNotBorrowedException() {
        super("Book is not borrowed.");
    }

    public BookNotBorrowedException(String message) {
        super(message);
    }
}
