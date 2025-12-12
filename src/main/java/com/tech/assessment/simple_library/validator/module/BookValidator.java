package com.tech.assessment.simple_library.validator.module;

import com.tech.assessment.simple_library.book.domain.BookEntity;
import com.tech.assessment.simple_library.book.repository.BookMetadataRepository;
import com.tech.assessment.simple_library.book.dto.RegisterBookRequest;
import com.tech.assessment.simple_library.book.domain.BookMetadataEntity;
import com.tech.assessment.simple_library.exception.custom.BookMetadataException;
import com.tech.assessment.simple_library.exception.custom.BookNotAvailableException;
import com.tech.assessment.simple_library.exception.custom.BookNotFoundException;
import com.tech.assessment.simple_library.validator.ValidationResult;
import com.tech.assessment.simple_library.validator.rule.RuleMaxLength;
import com.tech.assessment.simple_library.validator.rule.RuleRequired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookValidator implements ModuleValidator<RegisterBookRequest>{
    @Override
    public ValidationResult validate(RegisterBookRequest target) {
        ValidationResult result = ValidationResult.success();

        result.merge(new RuleRequired().validate("isbn", target.getIsbn()));
        result.merge(new RuleMaxLength(32).validate("isbn", target.getIsbn()));
        result.merge(new RuleRequired().validate("title", target.getTitle()));
        result.merge(new RuleMaxLength(255).validate("title", target.getTitle()));
        result.merge(new RuleRequired().validate("author", target.getAuthor()));
        result.merge(new RuleMaxLength(255).validate("author", target.getAuthor()));

        return result;
    }

    public ValidationResult validate(Integer bookId, Integer borrowerId) {
        ValidationResult result = ValidationResult.success();

        result.merge(new RuleRequired().validate("book-id", bookId));
        result.merge(new RuleRequired().validate("borrower_id", borrowerId));

        return result;
    }

    public void validateBookMetadata(BookMetadataRepository repository, RegisterBookRequest request){
        Optional<BookMetadataEntity> existing = repository.findByIsbn(request.getIsbn());

        if (existing.isPresent()) {
            BookMetadataEntity metadata = existing.get();
            if (!metadata.getTitle().equals(request.getTitle()) || !metadata.getAuthor().equals(request.getAuthor())) {
                throw new BookMetadataException();
            }
        }
    }

    public void validateBookExists(Optional<BookEntity> bookOpt, Integer bookId) {
        if(bookOpt.isEmpty()){
            throw new BookNotFoundException(String.format("Book id %s is not found.", bookId));
        }
    }

    public void validateBookAvailability(BookEntity bookEntity) {
        if(bookEntity.getCurrentlyBorrowedBy() != null){
            throw new BookNotAvailableException();
        }
    }
}
