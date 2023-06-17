package com.zaprogramujzycie.finanse.utils.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public UserNotFoundException() {
        super();
    }
}
