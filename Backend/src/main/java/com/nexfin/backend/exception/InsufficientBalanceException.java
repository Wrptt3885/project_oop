package com.nexfin.backend.exception;

import java.util.UUID;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(UUID userId) {
        super("Insufficient balance for user " + userId);
    }
}
