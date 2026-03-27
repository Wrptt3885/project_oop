package com.nexfin.backend.service.impl;

import com.nexfin.backend.config.JwtUtil;
import com.nexfin.backend.exception.DuplicateEmailException;
import com.nexfin.backend.exception.InvalidCredentialsException;
import com.nexfin.backend.model.dto.request.LoginRequest;
import com.nexfin.backend.model.dto.request.RegisterRequest;
import com.nexfin.backend.model.dto.response.LoginResponse;
import com.nexfin.backend.model.entity.User;
import com.nexfin.backend.model.entity.Wallet;
import com.nexfin.backend.model.enums.Currency;
import com.nexfin.backend.repository.UserRepository;
import com.nexfin.backend.repository.WalletRepository;
import com.nexfin.backend.service.AuthService;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final SecureRandom secureRandom = new SecureRandom();

    public AuthServiceImpl(
            UserRepository userRepository,
            WalletRepository walletRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    private String generateUniqueAccountNumber() {
        String accountNo;
        do {
            StringBuilder sb = new StringBuilder();
            sb.append(secureRandom.nextInt(9) + 1);
            for (int i = 0; i < 9; i++) {
                sb.append(secureRandom.nextInt(10));
            }
            accountNo = sb.toString();
        } while (userRepository.existsById(accountNo));

        return accountNo;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        String normalizedEmail = request.email().trim().toLowerCase();
        User user = userRepository.findByEmail(normalizedEmail)
                .filter(found -> passwordEncoder.matches(request.password(), found.getPassword()))
                .orElseThrow(InvalidCredentialsException::new);
        return new LoginResponse(jwtUtil.generateToken(user.getEmail()), user.getId(), user.getEmail());
    }

    @Override
    public LoginResponse register(RegisterRequest request) {
        String normalizedEmail = request.email().trim().toLowerCase();
        if (userRepository.findByEmail(normalizedEmail).isPresent()) {
            throw new DuplicateEmailException(normalizedEmail);
        }

        User user = new User(
                generateUniqueAccountNumber(),
                normalizedEmail,
                passwordEncoder.encode(request.password()),
                request.fullName().trim());
        userRepository.save(user);

        walletRepository.save(new Wallet(UUID.randomUUID(), user, BigDecimal.ZERO, Currency.THB));

        return new LoginResponse(jwtUtil.generateToken(user.getEmail()), user.getId(), user.getEmail());
    }
}
