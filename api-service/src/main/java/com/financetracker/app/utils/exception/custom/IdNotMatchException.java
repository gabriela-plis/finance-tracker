package com.financetracker.app.utils.exception.custom;

public class IdNotMatchException extends RuntimeException {
    public IdNotMatchException(String errorMessage) {
        super(errorMessage);
    }

    public IdNotMatchException() {
        super();
    }
}
