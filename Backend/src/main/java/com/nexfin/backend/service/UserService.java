// Backend/src/main/java/com/nexfin/backend/service/UserService.java
package com.nexfin.backend.service;

import com.nexfin.backend.model.dto.response.UserResponse;
import java.util.List;

public interface UserService {
    List<UserResponse> findAll();
    // ใช้ชื่อ findById ให้ตรงกับตัว Impl
    UserResponse findById(String userId); 
}