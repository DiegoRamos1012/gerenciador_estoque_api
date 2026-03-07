package com.diego_ramos.gerenciador_estoque.service;

import com.diego_ramos.gerenciador_estoque.config.JwtConfig;
import com.diego_ramos.gerenciador_estoque.domain.User;
import com.diego_ramos.gerenciador_estoque.dto.authDTO.AuthResponseDTO;
import com.diego_ramos.gerenciador_estoque.dto.authDTO.LoginRequestDTO;
import com.diego_ramos.gerenciador_estoque.dto.userDTO.UserResponseDTO;
import com.diego_ramos.gerenciador_estoque.exceptions.BusinessException;
import com.diego_ramos.gerenciador_estoque.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtConfig jwtConfig) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtConfig = jwtConfig;
    }

    public AuthResponseDTO login(LoginRequestDTO dto) {
        User user = userRepository.findByEmailIgnoreCase(dto.email())
                .orElseThrow(() -> new BusinessException("E-mail ou senha inválidos"));

        if (user.isDeleted()) {
            throw new BusinessException("Não é possível realizar login com um usuário desativado");
        }

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new BusinessException("E-mail ou senha inválidos");
        }

        String token = jwtConfig.generateToken(user);

        return new AuthResponseDTO(token, "Bearer", UserResponseDTO.from(user));
    }
}

