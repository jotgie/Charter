package com.charter.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandling {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandling.class);

    @ExceptionHandler(value = { ValidationException.class } )
    public ResponseEntity<String> handleValidationException(ValidationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { TransactionNotFoundException.class } )
    public ResponseEntity<String> handleTransactionNotFoundException(TransactionNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { CustomerNotFoundException.class } )
    public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { Exception.class } )
    public final ResponseEntity<String> handleAllExceptions(Exception e) {
        LOGGER.error("Internal error: " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

