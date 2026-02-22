package com.diego_ramos.gerenciador_estoque.service;

import com.diego_ramos.gerenciador_estoque.config.JwtConfig;
import com.diego_ramos.gerenciador_estoque.dto.authDTO.AuthResponseDTO;
import com.diego_ramos.gerenciador_estoque.dto.authDTO.LoginRequestDTO;
import com.diego_ramos.gerenciador_estoque.exceptions.BusinessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;

    public AuthService(UserDetailsService userDetailsService,
                       PasswordEncoder passwordEncoder,
                       JwtConfig jwtConfig) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtConfig = jwtConfig;
    }

    public AuthResponseDTO login(LoginRequestDTO dto) {
        UserDetails userDetails;

        try {
            userDetails = userDetailsService.loadUserByUsername(dto.email());
        } catch (Exception e) {
            throw new BusinessException("E-mail ou senha inválidos");
        }

        if (!passwordEncoder.matches(dto.password(), userDetails.getPassword())) {
            throw new BusinessException("E-mail ou senha inválidos");
        }

        String token = jwtConfig.generateToken(userDetails);

        return new AuthResponseDTO(token, "Bearer");
    }
}

