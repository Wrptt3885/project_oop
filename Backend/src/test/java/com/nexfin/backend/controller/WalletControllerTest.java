package com.nexfin.backend.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nexfin.backend.model.dto.response.WalletResponse;
import com.nexfin.backend.service.WalletService;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(WalletController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestSecurityBeans.class)
class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;

    @Test
    void shouldReturnWalletByUserId() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID walletId = UUID.randomUUID();

        when(walletService.getWalletByUserId(userId))
                .thenReturn(new WalletResponse(walletId, userId, new BigDecimal("250.00"), "THB"));

        mockMvc.perform(get("/api/wallets/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.walletId").value(walletId.toString()))
                .andExpect(jsonPath("$.userId").value(userId.toString()))
                .andExpect(jsonPath("$.balance").value(250.00))
                .andExpect(jsonPath("$.currency").value("THB"));
    }

    @Test
    void shouldTopUpWallet() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID walletId = UUID.randomUUID();

        when(walletService.topUp(eq(userId), eq(new com.nexfin.backend.model.dto.request.TopUpRequest(new BigDecimal("50.00"), "PROMPTPAY"))))
                .thenReturn(new WalletResponse(walletId, userId, new BigDecimal("300.00"), "THB"));

        mockMvc.perform(post("/api/wallets/{userId}/top-up", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "amount": 50.00,
                                  "reference": "PROMPTPAY"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(300.00))
                .andExpect(jsonPath("$.currency").value("THB"));
    }
}
