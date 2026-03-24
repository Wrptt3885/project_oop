package com.nexfin.backend.model.dto.response;

import com.nexfin.backend.model.entity.Wallet;
import java.math.BigDecimal;
import java.util.UUID;

public record WalletResponse(
        UUID walletId,
        UUID userId,
        BigDecimal balance,
        String currency) {

    public static WalletResponse from(Wallet wallet) {
        return new WalletResponse(
                wallet.getId(),
                wallet.getUser().getId(),
                wallet.getBalance(),
                wallet.getCurrency().name());
    }
}
