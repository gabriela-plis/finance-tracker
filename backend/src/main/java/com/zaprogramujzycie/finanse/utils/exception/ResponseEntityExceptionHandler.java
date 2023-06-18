package com.zaprogramujzycie.finanse.utils.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ResponseEntityExceptionHandler {

    @ExceptionHandler(DocumentNotFoundException.class)
    public ResponseEntity<?> handleDocumentNotFoundException() {
        return new ResponseEntity<>(NOT_FOUND);
    }
}
