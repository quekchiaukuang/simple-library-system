package com.tech.assessment.simple_library;

import com.tech.assessment.simple_library.book.domain.BookService;
import com.tech.assessment.simple_library.book.dto.GetBookRequest;
import com.tech.assessment.simple_library.book.dto.GetBookResponse;
import com.tech.assessment.simple_library.book.dto.RegisterBookRequest;
import com.tech.assessment.simple_library.book.dto.RegisterBookResponse;
import com.tech.assessment.simple_library.book.domain.BookEntity;
import com.tech.assessment.simple_library.book.domain.BookMetadataEntity;
import com.tech.assessment.simple_library.book.repository.BookMetadataRepository;
import com.tech.assessment.simple_library.book.repository.BookRepository;
import com.tech.assessment.simple_library.exception.custom.BookMetadataException;
import com.tech.assessment.simple_library.validator.ValidationResult;
import com.tech.assessment.simple_library.validator.module.BookValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestBookService {
    private static final Logger log = LoggerFactory.getLogger(TestBookService.class);

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMetadataRepository bookMetadataRepository;

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookValidator bookValidator;

    @Test
    void testGetBooks() {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(1);

        BookMetadataEntity bookMetadataEntity = new BookMetadataEntity();
        bookMetadataEntity.setIsbn("AA0A-0001");
        bookMetadataEntity.setTitle("Hellow World");
        bookMetadataEntity.setAuthor("Jaclyn");
        bookEntity.setBookMetadataEntity(bookMetadataEntity);

        List<BookEntity> list = List.of(bookEntity);

        when(bookRepository.findAll())
                .thenReturn(list);

        GetBookResponse bookResponse = bookService.getBooks(new GetBookRequest());

        assertEquals(1, bookResponse.getTotalReturn());
        assertEquals("AA0A-0001", bookResponse.getBooks().get(0).getIsbn());

        log.info("success get all books");
    }

    @Test
    void testRegisterBook(){
        RegisterBookRequest request = RegisterBookRequest.builder()
                .isbn("AA0A-0001")
                .title("Hello World")
                .author("johnson")
                .build();

        BookMetadataEntity metatdata = new BookMetadataEntity();
        metatdata.setIsbn("AA0A-0001");
        metatdata.setTitle("Hello World");
        metatdata.setAuthor("johnson");

        when(bookValidator.validate(any(RegisterBookRequest.class)))
                .thenReturn(ValidationResult.success());

        when(bookMetadataRepository.save(any(BookMetadataEntity.class)))
                .thenReturn(metatdata);

        when(bookRepository.save(any(BookEntity.class)))
                .thenAnswer(iv -> {
                    BookEntity book = iv.getArgument(0);
                    book.setId(1);
                    return book;
                });

        RegisterBookResponse response = bookService.registerBook(request);

        assertEquals("AA0A-0001", response.getIsbn());

        log.info("success register a book "+response.getIsbn());
    }

    @Test
    void testRegisterExistingIsbnWithDifferentTitle(){
        RegisterBookRequest request = RegisterBookRequest.builder()
                .isbn("AA0A-0001")
                .title("Hello World")
                .author("johnson")
                .build();

        BookMetadataEntity metatdata = new BookMetadataEntity();
        metatdata.setIsbn("AA0A-0001");
        metatdata.setTitle("Hello World");
        metatdata.setAuthor("johnson");

        when(bookValidator.validate(any(RegisterBookRequest.class)))
                .thenReturn(ValidationResult.success());

        doThrow(new BookMetadataException())
                .when(bookValidator)
                .validateBookMetadata(any(), any());

        assertThrows(BookMetadataException.class,
                () -> bookService.registerBook(request));

        log.info("success simulate register existing isbn with different title and author.");
    }
}
