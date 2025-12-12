package com.tech.assessment.simple_library.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRecordDto {
    private String reference;
    private String borrowed_at;
    private String due_date;
}
