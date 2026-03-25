package com.nexfin.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nexfin.backend.exception.WalletNotFoundException;
import com.nexfin.backend.model.dto.request.TopUpRequest;
import com.nexfin.backend.model.dto.response.WalletResponse;
import com.nexfin.backend.model.entity.Transaction;
import com.nexfin.backend.model.entity.User;
import com.nexfin.backend.model.entity.Wallet;
import com.nexfin.backend.model.enums.Currency;
import com.nexfin.backend.repository.TransactionRepository;
import com.nexfin.backend.repository.WalletRepository;
import com.nexfin.backend.service.impl.WalletServiceImpl;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private WalletServiceImpl walletService;

    @Test
    void shouldHandleTopUp() {
        UUID userId = UUID.randomUUID();
        Wallet wallet = createWallet(userId, "25.00");

        when(walletRepository.findByUserId(userId)).thenReturn(Optional.of(wallet));
        when(walletRepository.save(any(Wallet.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        WalletResponse response = walletService.topUp(userId, new TopUpRequest(new BigDecimal("75.00"), "PROMPTPAY"));

        assertEquals(new BigDecimal("100.00"), response.balance());
        assertEquals("THB", response.currency());
        verify(walletRepository, times(1)).save(wallet);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void shouldThrowWhenWalletMissing() {
        UUID userId = UUID.randomUUID();
        when(walletRepository.findByUserId(userId)).thenReturn(Optional.empty());

        assertThrows(WalletNotFoundException.class,
                () -> walletService.topUp(userId, new TopUpRequest(new BigDecimal("10.00"), "PROMPTPAY")));
    }

    private Wallet createWallet(UUID userId, String balance) {
        User user = new User(userId, "wallet@nexfin.com", "encoded-password", "Wallet Owner");
        return new Wallet(UUID.randomUUID(), user, new BigDecimal(balance), Currency.THB);
    }
}
