package com.nexfin.backend.service;

import com.nexfin.backend.model.dto.request.TopUpRequest;
import com.nexfin.backend.model.dto.response.WalletResponse;
import java.util.UUID;

public interface WalletService {
    WalletResponse getWalletByUserId(UUID userId);
    WalletResponse topUp(UUID userId, TopUpRequest request);
}
