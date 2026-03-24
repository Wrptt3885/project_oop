package com.nexfin.backend.model.dto.response;

import com.nexfin.backend.model.entity.Transaction;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record TransactionResponse(
        UUID transactionId,
        UUID sourceWalletId,
        UUID targetWalletId,
        BigDecimal amount,
        String type,
        String status,
        String reference,
        OffsetDateTime createdAt) {

    public static TransactionResponse from(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getSourceWallet().getId(),
                transaction.getTargetWallet().getId(),
                transaction.getAmount(),
                transaction.getType().name(),
                transaction.getStatus().name(),
                transaction.getReference(),
                transaction.getCreatedAt());
    }
}
