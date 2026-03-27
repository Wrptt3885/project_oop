package com.nexfin.backend.model.dto.response;

import com.nexfin.backend.model.entity.User;

public record UserResponse(
        String id, 
        String email, 
        String fullName
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFullName()
        );
    }
}