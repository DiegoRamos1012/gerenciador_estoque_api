package com.diego_ramos.gerenciador_estoque.service;

import com.diego_ramos.gerenciador_estoque.dto.UserDTO.UserResponseDTO;
import com.diego_ramos.gerenciador_estoque.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO create(UserReq)

}
