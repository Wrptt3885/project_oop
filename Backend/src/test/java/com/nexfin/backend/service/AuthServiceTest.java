package com.nexfin.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nexfin.backend.config.JwtUtil;
import com.nexfin.backend.exception.DuplicateEmailException;
import com.nexfin.backend.exception.InvalidCredentialsException;
import com.nexfin.backend.model.dto.request.LoginRequest;
import com.nexfin.backend.model.dto.request.RegisterRequest;
import com.nexfin.backend.model.dto.response.LoginResponse;
import com.nexfin.backend.model.entity.User;
import com.nexfin.backend.model.entity.Wallet;
import com.nexfin.backend.repository.UserRepository;
import com.nexfin.backend.repository.WalletRepository;
import com.nexfin.backend.service.impl.AuthServiceImpl;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        authService = new AuthServiceImpl(
                userRepository,
                walletRepository,
                passwordEncoder,
                new JwtUtil());
    }

    @Test
    void shouldRegisterWithNormalizedEmail() {
        RegisterRequest request = new RegisterRequest("Alice Example", " Alice@NexFin.com ", "StrongPass1");

        when(userRepository.findByEmail("alice@nexfin.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("StrongPass1")).thenReturn("encoded-password");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(walletRepository.save(any(Wallet.class))).thenAnswer(invocation -> invocation.getArgument(0));

        LoginResponse response = authService.register(request);

        assertEquals("alice@nexfin.com", response.email());
        assertNotNull(response.token());
        verify(userRepository).save(any(User.class));
        verify(walletRepository).save(any(Wallet.class));
    }

    @Test
    void shouldRejectDuplicateEmail() {
        when(userRepository.findByEmail("alice@nexfin.com"))
                .thenReturn(Optional.of(new User(UUID.randomUUID(), "alice@nexfin.com", "pw", "Alice")));

        assertThrows(DuplicateEmailException.class,
                () -> authService.register(new RegisterRequest("Alice", "alice@nexfin.com", "StrongPass1")));
    }

    @Test
    void shouldLoginWithNormalizedEmail() {
        User user = new User(UUID.randomUUID(), "alice@nexfin.com", "encoded-password", "Alice");
        when(userRepository.findByEmail("alice@nexfin.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("StrongPass1", "encoded-password")).thenReturn(true);

        LoginResponse response = authService.login(new LoginRequest(" Alice@NexFin.com ", "StrongPass1"));

        assertEquals(user.getId(), response.userId());
        assertEquals("alice@nexfin.com", response.email());
        assertNotNull(response.token());
    }

    @Test
    void shouldRejectInvalidCredentials() {
        when(userRepository.findByEmail("alice@nexfin.com")).thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class,
                () -> authService.login(new LoginRequest("alice@nexfin.com", "StrongPass1")));
    }
}
