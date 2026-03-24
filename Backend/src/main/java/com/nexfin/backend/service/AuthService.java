package com.nexfin.backend.service;

import com.nexfin.backend.model.dto.request.LoginRequest;
import com.nexfin.backend.model.dto.request.RegisterRequest;
import com.nexfin.backend.model.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    LoginResponse register(RegisterRequest request);
}
