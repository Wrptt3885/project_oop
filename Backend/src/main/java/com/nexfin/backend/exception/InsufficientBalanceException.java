package com.nexfin.backend.exception;

public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException(String userId) { 
        super("Insufficient balance for user: " + userId);
    }
}