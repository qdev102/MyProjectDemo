package com.example.product_management.exception;

public class AlreadyExitException extends RuntimeException {
    public AlreadyExitException(String message) {
        super(message);
    }
}
