package com.tech.assessment.simple_library.exception;

public class ErrorResponseConstant {
    public static class CODE {
        public static String REQUIRED = "E001";
        public static String INVALID = "E002";
        public static String CONFLICT = "E003";
        public static String NOT_FOUND = "E004";
        public static String LENGTH = "E005";
        public static String PROCESS = "E101";

        public static String BOOKMETADATA_CONFLICT = "EBM_001";
        public static String BOOK_NOT_AVAILABLE = "EB_NA_001";
        public static String BOOK_NOT_FOUND = "EB_NF_001";
        public static String BORROWER_DUPLICATED = "EBR_DP_001";
        public static String BORROWER_NOT_FOUND = "EBR_NF_001";
        public static String BOOK_NOT_BORROWED = "EB_NB_001";
        public static String BORROWER_NOT_MATCH = "EBR_NM_001";
    }
}
