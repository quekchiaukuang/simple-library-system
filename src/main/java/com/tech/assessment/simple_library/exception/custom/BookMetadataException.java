package com.tech.assessment.simple_library.exception.custom;

import lombok.Getter;

@Getter
public class BookMetadataException extends RuntimeException{

    public BookMetadataException() {
        super("ISBN is already registered with different title and author");
    }
}
