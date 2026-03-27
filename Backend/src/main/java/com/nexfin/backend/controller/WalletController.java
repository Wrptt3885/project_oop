package com.nexfin.backend.controller;

import com.nexfin.backend.model.dto.request.TopUpRequest;
import com.nexfin.backend.model.dto.response.WalletResponse;
import com.nexfin.backend.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/{userId}")
    public WalletResponse getWallet(@PathVariable String userId) {
        return walletService.getWalletByUserId(userId);
    }

    @PostMapping("/{userId}/top-up")
    public WalletResponse topUp(@PathVariable String userId, @Valid @RequestBody TopUpRequest request) {
        return walletService.topUp(userId, request);
    }
}
