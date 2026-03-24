package com.nexfin.backend.service.impl;

import com.nexfin.backend.exception.UserNotFoundException;
import com.nexfin.backend.model.dto.response.UserResponse;
import com.nexfin.backend.repository.UserRepository;
import com.nexfin.backend.service.UserService;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::from)
                .toList();
    }

    @Override
    public UserResponse findById(UUID userId) {
        return userRepository.findById(userId)
                .map(UserResponse::from)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
