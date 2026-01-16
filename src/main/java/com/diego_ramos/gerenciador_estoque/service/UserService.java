package com.diego_ramos.gerenciador_estoque.service;

import com.diego_ramos.gerenciador_estoque.domain.User;
import com.diego_ramos.gerenciador_estoque.dto.UserDTO.UserCreateDTO;
import com.diego_ramos.gerenciador_estoque.exceptions.BusinessException;
import com.diego_ramos.gerenciador_estoque.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void userCreate(UserCreateDTO dto) {
        if (userRepository.existsByNameIgnoreCase(dto.name())) {
            throw new BusinessException("Já existe um usuário com esse nome");
        }

        if (userRepository.existsByEmailIgnoreCase(dto.email())) {
            throw new BusinessException("Já existe um usuário com esse email");
        }

        String passwordHash = passwordEncoder.encode(dto.password());

        User user = User.create(dto.name(), dto.email(), passwordHash);

        userRepository.save(user);
    }
}
