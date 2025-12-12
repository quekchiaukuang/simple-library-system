-- Create schema
CREATE DATABASE IF NOT EXISTS simple_library
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_0900_as_cs;

USE simple_library;

-- ======================================================
-- Table 1: book_metadata
-- ======================================================
CREATE TABLE book_metadata (
    isbn VARCHAR(32) NOT NULL,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    PRIMARY KEY (isbn),
    UNIQUE KEY uq_book_metadata_isbn (isbn)
) ENGINE=InnoDB;

-- ======================================================
-- Table 2: borrower
-- ======================================================
CREATE TABLE borrower (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    
    UNIQUE KEY uq_borrower_email (email)
) ENGINE=InnoDB;

-- ======================================================
-- Table 3: book (Physical copies)
-- ======================================================
CREATE TABLE book (
    id INT AUTO_INCREMENT PRIMARY KEY,
    isbn VARCHAR(32) NOT NULL,
    currently_borrowed_by INT NULL,
    
    CONSTRAINT fk_book_isbn
        FOREIGN KEY (isbn) REFERENCES book_metadata(isbn)
        ON UPDATE CASCADE ON DELETE RESTRICT,
        
    CONSTRAINT fk_book_borrower
        FOREIGN KEY (currently_borrowed_by) REFERENCES borrower(id)
        ON UPDATE CASCADE ON DELETE SET NULL,
    
    INDEX idx_book_isbn (isbn),
    INDEX idx_book_currently_borrowed_by (currently_borrowed_by)
) ENGINE=InnoDB;

-- ======================================================
-- Table 4: borrow_record (Historical borrow/return)
-- ======================================================
CREATE TABLE borrow_record (
    id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT NOT NULL,
    borrower_id INT NOT NULL,
    borrowed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    returned_at TIMESTAMP NULL DEFAULT NULL,
	due_at TIMESTAMP NULL DEFAULT NULL,

    CONSTRAINT fk_br_book
        FOREIGN KEY (book_id) REFERENCES book(id)
        ON UPDATE CASCADE ON DELETE CASCADE,

    CONSTRAINT fk_br_borrower
        FOREIGN KEY (borrower_id) REFERENCES borrower(id)
        ON UPDATE CASCADE ON DELETE CASCADE,

    INDEX idx_br_book_id (book_id),
    INDEX idx_br_borrower_id (borrower_id)
) ENGINE=InnoDB;
