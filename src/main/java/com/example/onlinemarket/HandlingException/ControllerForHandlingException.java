package com.example.onlinemarket.HandlingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerForHandlingException {

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalStateException(IllegalStateException nullPointerException) {
        return new ResponseEntity<>(nullPointerException.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(NullPointerException nullPointerException){
        return new ResponseEntity<>(nullPointerException.getMessage(),HttpStatus.NOT_FOUND);
    }
}
