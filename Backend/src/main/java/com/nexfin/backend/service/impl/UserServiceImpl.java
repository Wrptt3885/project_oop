// Backend/src/main/java/com/nexfin/backend/service/impl/UserServiceImpl.java
package com.nexfin.backend.service.impl;

import com.nexfin.backend.exception.UserNotFoundException;
import com.nexfin.backend.model.dto.response.UserResponse;
import com.nexfin.backend.repository.UserRepository;
import com.nexfin.backend.service.UserService;
import java.util.List;
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
    // ชื่อต้องตรงกับ Interface และรับค่าเป็น String
    public UserResponse findById(String userId) { 
        return userRepository.findById(userId)
                .map(UserResponse::from)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}