package com.zaprogramujzycie.finanse.utils.exception;

public class DocumentNotFoundException extends RuntimeException {
    public DocumentNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public DocumentNotFoundException() {
        super();
    }
}
