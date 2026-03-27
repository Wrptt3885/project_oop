// Backend/src/main/java/com/nexfin/backend/exception/UserNotFoundException.java
package com.nexfin.backend.exception;

public class UserNotFoundException extends RuntimeException {
    // แก้จาก UUID เป็น String
    public UserNotFoundException(String userId) { 
        super("User not found with id: " + userId);
    }
}