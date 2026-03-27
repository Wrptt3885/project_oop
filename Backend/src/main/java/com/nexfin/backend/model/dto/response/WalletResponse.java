// Backend/src/main/java/com/nexfin/backend/model/dto/response/WalletResponse.java
package com.nexfin.backend.model.dto.response;

import com.nexfin.backend.model.entity.Wallet;
import java.math.BigDecimal;
import java.util.UUID;

public record WalletResponse(
        UUID walletId,
        String userId,
        BigDecimal balance,
        String currency) {

    public static WalletResponse from(Wallet wallet) {
        return new WalletResponse(
                wallet.getId(),
                wallet.getUser().getId(), // ตรงนี้ดึง String 10 หลักมาใส่ได้พอดีเป๊ะ
                wallet.getBalance(),
                wallet.getCurrency().name());
    }
}