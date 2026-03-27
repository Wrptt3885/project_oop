package com.nexfin.backend.service;

import com.nexfin.backend.model.dto.request.TransferRequest;
import com.nexfin.backend.model.dto.response.TransactionResponse;
import java.util.List;

public interface TransactionService {
    TransactionResponse transfer(TransferRequest request);
    List<TransactionResponse> findByUser(String userId);
}
