package com.tech.assessment.simple_library.book.api;

import com.tech.assessment.simple_library.book.domain.BookService;
import com.tech.assessment.simple_library.book.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/books")
    public ResponseEntity<RegisterBookResponse> registerBook(@RequestBody RegisterBookRequest request) {

        RegisterBookResponse response = bookService.registerBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/books")
    public ResponseEntity<GetBookResponse> getBooks(@RequestBody GetBookRequest request) {

        GetBookResponse response = bookService.getBooks(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/books/{book-id}/borrow")
    public ResponseEntity<BorrowBookResponse> borrowBook(
            @PathVariable(value = "book-id") Integer bookId, @RequestBody BorrowBookRequest request) {

        BorrowBookResponse response = bookService.borrowBook(bookId, request.getBorrowerId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/books/{book-id}/return")
    public ResponseEntity<ReturnBookResponse> returnBook(
            @PathVariable(value = "book-id") Integer bookId, @RequestBody BorrowBookRequest request) {

        ReturnBookResponse response = bookService.returnBook(bookId, request.getBorrowerId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
