package com.example.product_management.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String messenge) {
    super(messenge);
    }
}
