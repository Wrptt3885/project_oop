package com.nexfin.backend.model.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequest(
        @NotNull UUID fromUserId,
        @NotNull UUID toUserId,
        @NotNull @DecimalMin("0.01") BigDecimal amount,
        @NotBlank String reference) {
}
