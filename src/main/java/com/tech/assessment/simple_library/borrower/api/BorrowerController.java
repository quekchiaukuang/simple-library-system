package com.tech.assessment.simple_library.borrower.api;

import com.tech.assessment.simple_library.borrower.domain.BorrowerService;
import com.tech.assessment.simple_library.borrower.dto.RegisterBorrowerRequest;
import com.tech.assessment.simple_library.borrower.dto.RegisterBorrowerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class BorrowerController {

    private final BorrowerService borrowerService;

    public BorrowerController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    @PostMapping("/borrowers")
    public ResponseEntity<RegisterBorrowerResponse> registerBorrower(
            @RequestBody RegisterBorrowerRequest request) {

        RegisterBorrowerResponse response = borrowerService.registerBorrower(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
