package com.nexfin.backend.model.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record TransferRequest(
        @NotBlank String fromUserId,
        @NotBlank String toUserId,
        @NotNull @DecimalMin("0.01") BigDecimal amount,
        @NotBlank String reference) {
}
