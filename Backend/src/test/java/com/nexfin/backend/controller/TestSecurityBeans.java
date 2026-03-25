package com.nexfin.backend.controller;

import com.nexfin.backend.config.JwtAuthenticationFilter;
import com.nexfin.backend.config.JwtUtil;
import com.nexfin.backend.repository.UserRepository;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
class TestSecurityBeans {

    @Bean
    JwtUtil jwtUtil() {
        return new JwtUtil();
    }

    @Bean
    UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        return new JwtAuthenticationFilter(jwtUtil, userRepository);
    }
}
