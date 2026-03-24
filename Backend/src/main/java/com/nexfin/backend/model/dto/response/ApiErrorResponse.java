package com.nexfin.backend.model.dto.response;

import java.time.OffsetDateTime;

public record ApiErrorResponse(
        String message,
        String path,
        OffsetDateTime timestamp) {
}
