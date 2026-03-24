package com.nexfin.backend.service;

import com.nexfin.backend.model.dto.response.UserResponse;
import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserResponse> findAll();
    UserResponse findById(UUID userId);
}
