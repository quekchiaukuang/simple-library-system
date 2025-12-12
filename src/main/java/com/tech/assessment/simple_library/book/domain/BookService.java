package com.tech.assessment.simple_library.book.domain;

import com.tech.assessment.simple_library.book.dto.*;
import com.tech.assessment.simple_library.book.repository.BookMetadataRepository;
import com.tech.assessment.simple_library.book.repository.BookRepository;
import com.tech.assessment.simple_library.book.repository.BorrowRecordRepository;
import com.tech.assessment.simple_library.borrower.domain.BorrowerEntity;
import com.tech.assessment.simple_library.borrower.repository.BorrowerRepository;
import com.tech.assessment.simple_library.exception.custom.BookMetadataException;
import com.tech.assessment.simple_library.exception.custom.BookNotBorrowedException;
import com.tech.assessment.simple_library.exception.custom.BorrowRecordNotFoundException;
import com.tech.assessment.simple_library.exception.custom.BorrowerNotMatchedException;
import com.tech.assessment.simple_library.validator.ValidationResult;
import com.tech.assessment.simple_library.validator.module.BookValidator;
import com.tech.assessment.simple_library.validator.module.BorrowerValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;
    private final BookMetadataRepository metadataRepository;
    private final BorrowerRepository borrowerRepository;
    private final BorrowRecordRepository borrowRecordRepository;
    private final BorrowerValidator borrowerValidator;
    private final BookValidator bookValidator;

    public BookService(BookRepository bookRepository, BookMetadataRepository metadataRepository, BorrowerRepository borrowerRepository, BorrowRecordRepository borrowRecordRepository, BorrowerValidator borrowerValidator, BookValidator bookValidator) {
        this.bookRepository = bookRepository;
        this.metadataRepository = metadataRepository;
        this.borrowerRepository = borrowerRepository;
        this.borrowRecordRepository = borrowRecordRepository;
        this.borrowerValidator = borrowerValidator;
        this.bookValidator = bookValidator;
    }

    @Transactional
    public RegisterBookResponse registerBook(RegisterBookRequest request){
        ValidationResult result = bookValidator.validate(request);
        result.throwIfFailed();

        //Throw exception if ISBN is existed with different title, author
        bookValidator.validateBookMetadata(metadataRepository, request);

        try{
            //Get or create new book metadata
            BookMetadataEntity metadata = metadataRepository.findByIsbn(request.getIsbn())
                    .orElseGet(() -> {
                        BookMetadataEntity metadataEntity = new BookMetadataEntity();
                        metadataEntity.setIsbn(request.getIsbn());
                        metadataEntity.setAuthor(request.getAuthor());
                        metadataEntity.setTitle(request.getTitle());
                        metadataRepository.save(metadataEntity);

                        return metadataEntity;
                    });

            //create new book
            BookEntity bookEntity = new BookEntity();
            bookEntity.setBookMetadataEntity(metadata);
            bookEntity.setCurrentlyBorrowedBy(null);
            bookRepository.save(bookEntity);

            return RegisterBookResponse.builder()
                    .id(bookEntity.getId())
                    .isbn(metadata.getIsbn())
                    .title(metadata.getTitle())
                    .author(metadata.getAuthor())
                    .availableStatus(null == bookEntity.getCurrentlyBorrowedBy() ? "AVAILABLE": "BORROWED")
                    .borrowedBy(null)
                    .build();

        } catch (DataIntegrityViolationException ex) {
            log.error("Data integrity violation while registering book", ex);
            throw new BookMetadataException();
        }
    }

    @Transactional
    public BorrowBookResponse borrowBook(Integer bookId, Integer borrowerId){
        ValidationResult result = bookValidator.validate(bookId, borrowerId);
        result.throwIfFailed();

        //find book exists
        Optional<BookEntity> bookOpt = bookRepository.findByIdForUpdate(bookId);
        bookValidator.validateBookExists(bookOpt, bookId);

        //find borrower exists
        Optional<BorrowerEntity> borrowerOpt = borrowerRepository.findById(borrowerId);
        borrowerValidator.validateBorrowerExists(borrowerOpt, borrowerId);

        BookEntity bookEntity = bookOpt.get();
        BorrowerEntity borrowerEntity = borrowerOpt.get();

        //find if book is borrowed
        bookValidator.validateBookAvailability(bookEntity);

        //borrow the book
        bookEntity.setCurrentlyBorrowedBy(borrowerEntity);
        bookRepository.save(bookEntity);

        //update borrow record
        BorrowRecordEntity recordEntity = new BorrowRecordEntity();
        recordEntity.setBookEntity(bookEntity);
        recordEntity.setBorrower(borrowerEntity);
        recordEntity.setBorrowedAt(LocalDateTime.now());
        recordEntity.setDueAt(recordEntity.getBorrowedAt().plusDays(20));
        borrowRecordRepository.save(recordEntity);

        return BorrowBookResponse.builder()
                .book(BookDto.builder()
                        .id(bookEntity.getId())
                        .isbn(bookEntity.getBookMetadataEntity().getIsbn())
                        .title(bookEntity.getBookMetadataEntity().getTitle())
                        .author(bookEntity.getBookMetadataEntity().getAuthor())
                        .availableStatus("BORROWED")
                        .build())
                .borrow_record(BorrowRecordDto.builder()
                        .reference(recordEntity.getId().toString())
                        .borrowed_at(recordEntity.getBorrowedAt().toString())
                        .due_date(recordEntity.getDueAt().toString())
                        .build())
                .build();
    }

    @Transactional
    public ReturnBookResponse returnBook(Integer bookId, Integer borrowerId) {
        ValidationResult result = bookValidator.validate(bookId, borrowerId);
        result.throwIfFailed();

        //find book exists
        Optional<BookEntity> bookOpt = bookRepository.findByIdForUpdate(bookId);
        bookValidator.validateBookExists(bookOpt, bookId);

        //find borrower exists
        Optional<BorrowerEntity> borrowerOpt = borrowerRepository.findById(borrowerId);
        borrowerValidator.validateBorrowerExists(borrowerOpt, borrowerId);

        BookEntity bookEntity = bookOpt.get();
        BorrowerEntity borrowerEntity = borrowerOpt.get();

        //check if book is borrowed
        if(bookEntity.getCurrentlyBorrowedBy() == null){
            throw new BookNotBorrowedException();
        }

        //find if borrowedBy matches borrowedId
        if(!bookEntity.getCurrentlyBorrowedBy().getId().equals(borrowerId)){
            throw new BorrowerNotMatchedException();
        }

        //reset borrowed by
        bookEntity.setCurrentlyBorrowedBy(null);
        bookRepository.save(bookEntity);

        //update borrow record's returned date
        BorrowRecordEntity recordEntity = borrowRecordRepository.findBorrowingBook(
                bookEntity.getId(), borrowerEntity.getId())
                .orElseThrow(() -> new BorrowRecordNotFoundException());

        recordEntity.setReturnedAt(LocalDateTime.now());
        borrowRecordRepository.save(recordEntity);

        return ReturnBookResponse.builder()
                .bookId(bookEntity.getId())
                .borrowerId(borrowerEntity.getId())
                .returnedAt(recordEntity.getReturnedAt().toString())
                .build();
    }

    public GetBookResponse getBooks(GetBookRequest request) {
        List<BookEntity> books = bookRepository.findAll();

        return GetBookResponse.builder()
                .totalReturn(books.size())
                .books(books.stream().map(b -> {
                        RegisterBookBorrowerDto borrower = null;

                        if(b.getCurrentlyBorrowedBy() != null){
                            borrower = RegisterBookBorrowerDto.builder()
                                    .id(b.getCurrentlyBorrowedBy().getId())
                                    .name(b.getCurrentlyBorrowedBy().getName())
                                    .email(b.getCurrentlyBorrowedBy().getEmail()).build();
                        }

                        return BookDto.builder()
                                .id(b.getId())
                                .isbn(b.getBookMetadataEntity().getIsbn())
                                .title(b.getBookMetadataEntity().getTitle())
                                .author(b.getBookMetadataEntity().getAuthor())
                                .availableStatus(b.getCurrentlyBorrowedBy() == null ? "AVAILABLE" : "BORROWED")
                                .borrowerBy(borrower)
                                .build();

                }).collect(Collectors.toList())
                ).build();
    }
}
