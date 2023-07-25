package com.financetracker.app.utils.exception;

import com.financetracker.app.utils.exception.custom.DocumentNotFoundException;
import com.financetracker.app.utils.exception.custom.IdNotMatchException;
import com.financetracker.app.utils.exception.custom.UserAlreadyExistException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ResponseEntityExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(DocumentNotFoundException.class)
    public void handleDocumentNotFoundException() {}

    @ResponseStatus(CONFLICT)
    @ExceptionHandler({
        UserAlreadyExistException.class,
        IdNotMatchException.class
    })
    public void handleConflictExceptions() {}

    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class
    })
    public void handleFailedDTOValidationExceptions() {}

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public void handleBadCredentialsException() {}
}
