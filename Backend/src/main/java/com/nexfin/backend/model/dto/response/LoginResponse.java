package com.nexfin.backend.model.dto.response;

import java.util.UUID;

public record LoginResponse(
        String token,
        UUID userId,
        String email) {
}
