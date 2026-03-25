package com.nexfin.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.nexfin.backend.exception.InsufficientBalanceException;
import com.nexfin.backend.exception.InvalidTransactionException;
import com.nexfin.backend.model.dto.request.TransferRequest;
import com.nexfin.backend.model.dto.response.TransactionResponse;
import com.nexfin.backend.model.entity.Transaction;
import com.nexfin.backend.model.entity.User;
import com.nexfin.backend.model.entity.Wallet;
import com.nexfin.backend.model.enums.Currency;
import com.nexfin.backend.repository.TransactionRepository;
import com.nexfin.backend.repository.WalletRepository;
import com.nexfin.backend.service.impl.TransactionServiceImpl;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void shouldTransferBetweenWallets() {
        UUID senderId = UUID.randomUUID();
        UUID receiverId = UUID.randomUUID();
        Wallet sender = createWallet(senderId, "200.00");
        Wallet receiver = createWallet(receiverId, "50.00");

        when(walletRepository.findByUserId(senderId)).thenReturn(Optional.of(sender));
        when(walletRepository.findByUserId(receiverId)).thenReturn(Optional.of(receiver));
        when(walletRepository.save(any(Wallet.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TransactionResponse response = transactionService.transfer(
                new TransferRequest(senderId, receiverId, new BigDecimal("75.00"), "PAYMENT_001"));

        assertEquals(new BigDecimal("125.00"), sender.getBalance());
        assertEquals(new BigDecimal("125.00"), receiver.getBalance());
        assertEquals("TRANSFER", response.type());
        assertEquals("PAYMENT_001", response.reference());
    }

    @Test
    void shouldRejectSameSourceAndTargetUser() {
        UUID userId = UUID.randomUUID();

        assertThrows(InvalidTransactionException.class,
                () -> transactionService.transfer(new TransferRequest(userId, userId, new BigDecimal("10.00"), "SELF")));
    }

    @Test
    void shouldRejectWhenBalanceInsufficient() {
        UUID senderId = UUID.randomUUID();
        UUID receiverId = UUID.randomUUID();
        Wallet sender = createWallet(senderId, "20.00");
        Wallet receiver = createWallet(receiverId, "50.00");

        when(walletRepository.findByUserId(senderId)).thenReturn(Optional.of(sender));
        when(walletRepository.findByUserId(receiverId)).thenReturn(Optional.of(receiver));

        assertThrows(InsufficientBalanceException.class,
                () -> transactionService.transfer(new TransferRequest(senderId, receiverId, new BigDecimal("75.00"), "PAYMENT_001")));
    }

    private Wallet createWallet(UUID userId, String balance) {
        User user = new User(userId, userId + "@nexfin.com", "encoded-password", "User");
        return new Wallet(UUID.randomUUID(), user, new BigDecimal(balance), Currency.THB);
    }
}
