package com.nexfin.backend.service.impl;

import com.nexfin.backend.exception.WalletNotFoundException;
import com.nexfin.backend.model.dto.request.TopUpRequest;
import com.nexfin.backend.model.dto.response.WalletResponse;
import com.nexfin.backend.model.entity.Transaction;
import com.nexfin.backend.model.entity.Wallet;
import com.nexfin.backend.model.enums.TransactionStatus;
import com.nexfin.backend.model.enums.TransactionType;
import com.nexfin.backend.repository.TransactionRepository;
import com.nexfin.backend.repository.WalletRepository;
import com.nexfin.backend.service.WalletService;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    public WalletServiceImpl(WalletRepository walletRepository, TransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public WalletResponse getWalletByUserId(String userId) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new WalletNotFoundException(userId));
        return WalletResponse.from(wallet);
    }

    @Override
    @Transactional
    public WalletResponse topUp(String userId, TopUpRequest request) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new WalletNotFoundException(userId));
        wallet.setBalance(wallet.getBalance().add(request.amount()));
        Wallet updatedWallet = walletRepository.save(wallet);

        Transaction transaction = new Transaction(
                UUID.randomUUID(),
                updatedWallet,
                updatedWallet,
                request.amount(),
                TransactionType.TOPUP,
                TransactionStatus.SUCCESS,
                OffsetDateTime.now(),
                request.reference().trim());

        transactionRepository.save(transaction);
        return WalletResponse.from(updatedWallet);
    }
}
