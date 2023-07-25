package com.financetracker.app.utils.exception.custom;

public class DocumentNotFoundException extends RuntimeException {
    public DocumentNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public DocumentNotFoundException() {
        super();
    }
}
