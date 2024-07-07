package com.example.onlinemarket.exceptions;

public class OperationForbiddenException extends RuntimeException {
    public OperationForbiddenException(String message) {
        super(message);
    }
}
