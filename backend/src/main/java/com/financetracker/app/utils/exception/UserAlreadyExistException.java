package com.financetracker.app.utils.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String errorMessage) {
        super(errorMessage);
    }

    public UserAlreadyExistException() {
        super();
    }
}
