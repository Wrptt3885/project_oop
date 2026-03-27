package com.nexfin.backend.service.impl;

import com.nexfin.backend.exception.InsufficientBalanceException;
import com.nexfin.backend.exception.InvalidTransactionException;
import com.nexfin.backend.exception.WalletNotFoundException;
import com.nexfin.backend.model.dto.request.TransferRequest;
import com.nexfin.backend.model.dto.response.TransactionResponse;
import com.nexfin.backend.model.entity.Transaction;
import com.nexfin.backend.model.entity.Wallet;
import com.nexfin.backend.model.enums.TransactionStatus;
import com.nexfin.backend.model.enums.TransactionType;
import com.nexfin.backend.repository.TransactionRepository;
import com.nexfin.backend.repository.WalletRepository;
import com.nexfin.backend.service.TransactionService;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, WalletRepository walletRepository) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
    }

    @Override
    @Transactional
    public TransactionResponse transfer(TransferRequest request) {
        if (request.fromUserId().equals(request.toUserId())) {
            throw new InvalidTransactionException("Source and target users must be different");
        }

        Wallet source = walletRepository.findByUserId(request.fromUserId())
                .orElseThrow(() -> new WalletNotFoundException(request.fromUserId()));
        Wallet target = walletRepository.findByUserId(request.toUserId())
                .orElseThrow(() -> new WalletNotFoundException(request.toUserId()));

        if (source.getBalance().compareTo(request.amount()) < 0) {
            throw new InsufficientBalanceException(source.getUser().getId());
        }

        source.setBalance(source.getBalance().subtract(request.amount()));
        target.setBalance(target.getBalance().add(request.amount()));

        walletRepository.save(source);
        walletRepository.save(target);

        Transaction transaction = new Transaction(
                UUID.randomUUID(),
                source,
                target,
                request.amount(),
                TransactionType.TRANSFER,
                TransactionStatus.SUCCESS,
                OffsetDateTime.now(),
                request.reference().trim());

        return TransactionResponse.from(transactionRepository.save(transaction));
    }

    @Override
    public List<TransactionResponse> findByUser(String userId) {
        return transactionRepository.findAllBySourceWalletUserIdOrTargetWalletUserIdOrderByCreatedAtDesc(userId, userId)
                .stream()
                .map(TransactionResponse::from)
                .toList();
    }
}
