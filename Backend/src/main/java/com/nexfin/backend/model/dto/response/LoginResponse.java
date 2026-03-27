package com.nexfin.backend.model.dto.response;

public record LoginResponse(
    String token, 
    String userId,
    String email
) {}