package com.cryptotracker.portfolio.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerErrorHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(ResourceNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(Map.of("message", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(Map.of("message", errors), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Object> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        return new ResponseEntity<>(Map.of("message", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NameEmptyException.class)
    public ResponseEntity<Object> handleNameEmpty(NameEmptyException ex) {
        return new ResponseEntity<>(Map.of("message", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidCryptoException.class)
        public ResponseEntity<Object> handleCryptoInvali(InvalidCryptoException ex){
        return new ResponseEntity<>(Map.of("message",ex.getMessage()),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(QuantityZeroBelow.class)
    public ResponseEntity<Object> handleBelowZero(QuantityZeroBelow ex){
        return new ResponseEntity<>(Map.of("message",ex.getMessage()),HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(EmailEmptyException.class)
    public ResponseEntity<Object>  handleEmailEmpty(EmailEmptyException ex){
        return new ResponseEntity<>(Map.of("message", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordEmptyException.class)
    public ResponseEntity<Object>  handlePasswordEmpty(PasswordEmptyException ex){
        return new ResponseEntity<>(Map.of("message", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

}

