package com.nexfin.backend.exception;

public class WalletNotFoundException extends RuntimeException {
    public WalletNotFoundException(String userId) {
        super("Wallet not found for user " + userId);
    }
}
