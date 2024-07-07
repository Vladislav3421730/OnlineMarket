package com.example.onlinemarket.HandlingException;

import com.example.onlinemarket.exceptions.DataNotFoundException;
import com.example.onlinemarket.exceptions.ErrorResponse;
import com.example.onlinemarket.exceptions.OperationForbiddenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerForHandlingException {

    @ExceptionHandler(OperationForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(OperationForbiddenException operationForbiddenException) {
        ErrorResponse errorResponse=new ErrorResponse(operationForbiddenException.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNullPointerException(DataNotFoundException dataNotFoundException){
        ErrorResponse errorResponse=new ErrorResponse(dataNotFoundException.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);

    }
}
