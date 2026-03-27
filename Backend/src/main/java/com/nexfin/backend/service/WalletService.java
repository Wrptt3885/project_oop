package com.nexfin.backend.service;

import com.nexfin.backend.model.dto.request.TopUpRequest;
import com.nexfin.backend.model.dto.response.WalletResponse;

public interface WalletService {
    WalletResponse getWalletByUserId(String userId);
    WalletResponse topUp(String userId, TopUpRequest request);
}
